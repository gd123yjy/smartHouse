
// EDP includes
#include "cJSON.h"
#include "EdpKit.h"

#include "tradoff.h"
#include "edpCommon.h"
#include "common.h"
#include "uart_if.h"

#include <socket.h>

//tradoff
#include "rom_map.h"
#include "gpio_if.h"
#include "protocol.h"
#include "hw_types.h"
#include "prcm.h"

#define true	1
#define false	0

char g_cBsdBuf[BUF_SIZE];
char send_buf[BUFSIZE];
char recv_buffer[RECV_BUFSIZ];
unsigned long ulSecs;
unsigned short usMsec;


int BsdTcpClient(unsigned int ulDestinationIp, unsigned short usPort);
int32 DoSend(int32 sockfd, const char* buffer, uint32 len);
int write_func(int arg);
int recv_func(int arg);
void hexdump(const unsigned char *buf, uint32 num);
float getTemperature();
int client_process_func(int arg);

void AppTask()
{
    int sockfd, ret;
    EdpPacket* send_pkg;


    PRCMRTCInUseSet();
    PRCMRTCSet(0, 0);
    PRCMRTCGet(&ulSecs, &usMsec);
    printf("AppTask start at %ul.%ud\n", ulSecs, usMsec);

    while(1)
    {
		g_sockfd = Open(SERVER_ADDR_HEX, SERVER_PORT);
		sockfd = g_sockfd;
		if(sockfd < 0)
		{
			Close(sockfd);
			return;
		}

		send_pkg = PacketConnect1(DEV_ID, API_KEY);
		ret = DoSend(sockfd, send_pkg->_data, send_pkg->_write_pos);
		DeleteBuffer(&send_pkg);

		if(ret < 0)
		{
			printf("connect error!\n");
		}


        client_process_func(sockfd);

        Close(sockfd);

    }

}

int client_process_func(int arg)
{
	int sockfd = arg;
	fd_set read_set;
	fd_set write_set;

	while(1)
	{
		FD_ZERO(&read_set);
		FD_ZERO(&write_set);

		FD_SET(sockfd, &read_set);
		FD_SET(sockfd, &write_set);

		if(select(sockfd+1, &read_set, &write_set, 0, 0) < 0)
		{
			continue;
		}

		if(FD_ISSET(sockfd, &read_set)){
			if(recv_func(sockfd)<0)
			{
				break;
			}
		}

		if(FD_ISSET(sockfd, &write_set)){
			if(write_func(sockfd)<0)
			{
				break;
			}
		}

	}
	return -1;
}

int recv_func(int arg)
{
    int sockfd = arg;
    int error = 0;
    int n, rtn;
    uint8 mtype, jsonorbin;

    RecvBuffer* recv_buf = NewBuffer();
    EdpPacket* pkg;

    char* src_devid;
    char* push_data;
    uint32 push_datalen;

    cJSON* save_json;
    char* save_json_str;

    cJSON* desc_json;
    char* desc_json_str;
    char* save_bin;
    uint32 save_binlen;
    int ret = 0;

    do
    {
        /*接收网络数据*/
        n = Recv(sockfd, recv_buffer, RECV_BUFSIZ, 0);
        if(n <= 0)
        {
            printf("recv error, bytes: %d\n", n);
//            error = -1;
            break;
        }
#ifdef DEBUG
        printf("recv from server, bytes: %d\n", n);
#endif
        WriteBytes(recv_buf, recv_buffer, n);
        while(1)
        {
            if((pkg = GetEdpPacket(recv_buf)) == 0)
            {
#ifdef DEBUG
                printf("nothing received...\n");
#endif
                break;
            }
            /*解析EDP数据包的类型*/
            mtype = EdpPacketType(pkg);
            switch(mtype)
            {
            	case CMDREQ:
            		handlePkg(pkg);
            		break;
            	/* 命令响应 */
            	case CMDRESP:

            		break;
                /*连接响应数据包解析*/
                case CONNRESP:
                    rtn = UnpackConnectResp(pkg);
                    /*串口打印解析结果*/
                    printf("recv connect resp, rtn: %d\n", rtn);
                    break;
                /*存储（&转发）数据包解析*/
                case PUSHDATA:
                    UnpackPushdata(pkg, &src_devid, &push_data, &push_datalen);
                    /*串口打印解析结果*/
                    printf("recv push data, src_devid: %s, push_data: %s, len: %d\n",
                           src_devid, push_data, push_datalen);
                    free(src_devid);
                    free(push_data);
                    break;
                /*转发（透传）数据包解析*/
                case SAVEDATA:
                    if(UnpackSavedata(pkg, &src_devid, &jsonorbin) == 0)
                    {
                        if(jsonorbin == 0x01)
                        {
                            /* json */
                            ret = UnpackSavedataJson(pkg, &save_json);
                            save_json_str=cJSON_Print(save_json);
                            /*串口打印解析结果*/
                            printf("recv save data json, ret = %d, src_devid: %s, json: %s\n",
                                   ret, src_devid, save_json_str);
                            free(save_json_str);
                            cJSON_Delete(save_json);
                        }
                        else if(jsonorbin == 0x02)
                        {
                            /* bin */
                            UnpackSavedataBin(pkg, &desc_json, (uint8**)&save_bin, &save_binlen);
                            desc_json_str=cJSON_Print(desc_json);
                            /*串口打印解析结果*/
                            printf("recv save data bin, src_devid: %s, desc json: %s, bin: %s, binlen: %d\n",
                                   src_devid, desc_json_str, save_bin, save_binlen);
                            free(desc_json_str);
                            cJSON_Delete(desc_json);
                            free(save_bin);
                        }
                        free(src_devid);
                    }
                    break;
                /*心跳响应数据包解析*/
                case PINGRESP:
                    UnpackPingResp(pkg);
                    printf("recv ping resp\n");
                    break;
                default:
                    printf("recv failed...\n");
                    write_func(sockfd);
                    hexdump(pkg->_data, pkg->_write_pos);
                    break;
            }
            DeleteBuffer(&pkg);
        }
    }
    while(0);
    DeleteBuffer(&recv_buf);
    return error;
}

int write_func(int sock)
{
    int sockfd = sock;
    EdpPacket* send_pkg;
    cJSON *save_json;
    int32 ret = 0;
    float temp;
    unsigned char lightStatus[MCU_LIGHT_COUNT];
    unsigned long ulSecsNow;
    unsigned short usMsecNow;

    PRCMRTCGet(&ulSecsNow, &usMsecNow);

    // 10s
    if(ulSecsNow-ulSecs < 10)
    {
    	return 0;
    }
//    	PRCMMCUReset(true);
    ulSecs = ulSecsNow;
    usMsec = usMsecNow;
#ifdef DEBUG
    printf("write_func start at %ul.%ud\n", ulSecs, usMsec);
#endif
    save_json = cJSON_CreateObject();

    temp = getTemperature();
    lightStatus[MCU_LIGHT_INDEX(MCU_LIGHT_BATHROOM)] = GPIO_IF_LedStatus(MCU_LIGHT_BATHROOM);
    lightStatus[MCU_LIGHT_INDEX(MCU_LIGHT_KITCHEN)] = GPIO_IF_LedStatus(MCU_LIGHT_KITCHEN);
    lightStatus[MCU_LIGHT_INDEX(MCU_LIGHT_BEDROOM)] = GPIO_IF_LedStatus(MCU_LIGHT_BEDROOM);

    cJSON_AddNumberToObject(save_json, "temperature", temp);
    cJSON_AddNumberToObject(save_json, "bathroom_light", lightStatus[MCU_LIGHT_INDEX(MCU_LIGHT_BATHROOM)]);
    cJSON_AddNumberToObject(save_json, "bedroom_light", lightStatus[MCU_LIGHT_INDEX(MCU_LIGHT_BEDROOM)]);
    cJSON_AddNumberToObject(save_json, "kitchen_light", lightStatus[MCU_LIGHT_INDEX(MCU_LIGHT_KITCHEN)]);

    if(NULL == save_json)
    {
        return -1;
    }

    send_pkg = PacketSavedataJson(DEV_ID, save_json, kTypeSimpleJsonWithoutTime, 0);
#ifdef DEBUG
    printf("json:\n%s\n", save_json->valuestring);
#endif
    if(NULL == send_pkg)
    {
        return -1;
    }
    cJSON_Delete(save_json);
    /*发送EDP数据包上传数据*/
    ret = DoSend(sockfd, send_pkg->_data, send_pkg->_write_pos);
    DeleteBuffer(&send_pkg);
    return ret;
}


int32 DoSend(int32 sockfd, const char* buffer, uint32 len)
{
    int32 total  = 0;
    int32 n = 0;

#ifdef DEBUG
    //added by tradoff
    printf("DoSend start... prepare to write:  \n%s\n", buffer);
#endif

    while (len != total)
    {
        /* 试着发送len - total个字节的数据 */
    	//tradoff
//        n = Send(sockfd,buffer + total,len - total,MSG_NOSIGNAL);
        n = Send(sockfd,buffer + total,len - total, 0);

        if (n <= 0)
        {
            fprintf(stderr, "ERROR writing to socket\n");

            //added by tradoff
            printf("failed to write:\n%s at index %d\n ", buffer+total, total);

            return n;
        }
        /* 成功发送了n个字节的数据 */
        total += n;
    }
    /* wululu test print send bytes */
    hexdump((const unsigned char *)buffer, len);
    return total;
}

/*
 * buffer按十六进制输出
 */
void hexdump(const unsigned char *buf, uint32 num)
{
#ifndef	__SKIP_HEXDUMP
    uint32 i = 0;
    for (; i < num; i++)
    {
        printf("0x%02X ", buf[i]);
        if ((i+1)%8 == 0)
            printf("\n");
    }
    printf("\n");
#endif
}
//tradoff: get the temperature
float getTemperature()
{
	float Temp;

	TMP006DrvGetTemp(&Temp);
	return Temp;
}

int BsdTcpClient(unsigned int ulDestinationIp, unsigned short usPort)
{
    int             iCounter;
    SlSockAddrIn_t  sAddr;
    int             iAddrSize;
    int             iSockID;
    int             iStatus;

    // filling the buffer
    for (iCounter=0 ; iCounter<BUF_SIZE ; iCounter++)
    {
        g_cBsdBuf[iCounter] = (char)(iCounter % 10);
    }

//    sTestBufLen  = BUF_SIZE;

    //filling the TCP server socket address
    sAddr.sin_family = SL_AF_INET;
    sAddr.sin_port = sl_Htons((unsigned short)usPort);
    sAddr.sin_addr.s_addr = sl_Htonl((unsigned int)ulDestinationIp);

    iAddrSize = sizeof(SlSockAddrIn_t);

    // creating a TCP socket
    iSockID = sl_Socket(SL_AF_INET,SL_SOCK_STREAM, 0);
    if( iSockID < 0 )
    {
        ASSERT_ON_ERROR(SOCKET_CREATE_ERROR);
    }

    // connecting to TCP server
    iStatus = sl_Connect(iSockID, ( SlSockAddr_t *)&sAddr, iAddrSize);
    if( iStatus < 0 )
    {
        // error
        sl_Close(iSockID);
        ASSERT_ON_ERROR(CONNECT_ERROR);
    }

    return iSockID;
}

