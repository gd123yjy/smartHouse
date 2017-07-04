#ifndef _TRADOFF_H
#define _TRADOFF_H

#define __SKIP_HEXDUMP

#define PID				"85240"		//产品ID
#define USER_ID			"58630"		//用户ID
#define API_KEY         "8T=kDEqacwFHyK79OCYz8HCB9yY=" //APIKey
#define DEV_CONTROLLER

#ifdef	DEV_CONTROLLER
#define DEV_ID          "5794867" //设备ID
#endif
#ifdef	DEV_BATHROOM
#define DEV_ID          "5696229" //设备ID
#endif
#ifdef	DEV_BEDROOM
#define DEV_ID          "5645900" //设备ID
#endif
#ifdef	DEV_UNDERDOOR
#define DEV_ID          "5645891" //设备ID
#endif
#ifdef	DEV_KITCHEN
#define DEV_ID          "5529300" //设备ID
#endif

#define SERVER_ADDR     "jjfaedp.hedevice.com"    //OneNet EDP 服务器地址
#define SERVER_ADDR_HEX	0xb7e62827		//183.230.40.39
#define SERVER_PORT		876            //OneNet EDP 服务器端口

/*
#define SERVER_ADDR     "api.heclouds.com"    //OneNet Restful API 服务器地址
#define SERVER_ADDR_HEX	0xb7e62821		//183.230.40.33
#define SERVER_PORT		80            //OneNet Restful API 服务器端口
*/


#define BUFSIZE		1024
#define BUF_SIZE	1400

/*----------------------------错误码-----------------------------------------*/
#define ERR_CREATE_SOCKET   -1
#define ERR_HOSTBYNAME      -2
#define ERR_CONNECT         -3
#define ERR_SEND            -4
#define ERR_TIMEOUT         -5
#define ERR_RECV            -6
/*---------------统一linux和windows上的Socket api----------------------------*/
#ifndef htonll
#ifdef _BIG_ENDIAN
#define htonll(x)   (x)
#define ntohll(x)   (x)
#else
#define htonll(x)   ((((uint64)htonl(x)) << 32) + htonl(x >> 32))
#define ntohll(x)   ((((uint64)ntohl(x)) << 32) + ntohl(x >> 32))
#endif
#endif


// Application specific status/error codes
typedef enum{
    // Choosing -0x7D0 to avoid overlap w/ host-driver's error codes
    LAN_CONNECTION_FAILED = -0x7D0,
    INTERNET_CONNECTION_FAILED = LAN_CONNECTION_FAILED - 1,

    SOCKET_CREATE_ERROR = INTERNET_CONNECTION_FAILED - 1,
    BIND_ERROR = SOCKET_CREATE_ERROR - 1,
    LISTEN_ERROR = BIND_ERROR -1,
    SOCKET_OPT_ERROR = LISTEN_ERROR -1,
    CONNECT_ERROR = SOCKET_OPT_ERROR -1,
    ACCEPT_ERROR = CONNECT_ERROR - 1,
    SEND_ERROR = ACCEPT_ERROR -1,
    RECV_ERROR = SEND_ERROR -1,
    SOCKET_CLOSE_ERROR = RECV_ERROR -1,
    DEVICE_NOT_IN_STATION_MODE = SOCKET_CLOSE_ERROR - 1,
    STATUS_CODE_MAX = -0xBB8
}e_AppStatusCodes;

int g_sockfd;

void AppTask();
#define Open(ip, port)  BsdTcpClient(ip, port)


#ifdef _LINUX
#define Socket(a,b,c)          socket(a,b,c)
#define Connect(a,b,c)         connect(a,b,c)
#define Close(a)               close(a)
#define Read(a,b,c)            read(a,b,c)
#define Recv(a,b,c,d)          recv(a, (void *)b, c, d)
#define Select(a,b,c,d,e)      select(a,b,c,d,e)
#define Send(a,b,c,d)          send(a, (const int8 *)b, c, d)
#define Write(a,b,c)           write(a,b,c)
#define GetSockopt(a,b,c,d,e)  getsockopt((int)a,(int)b,(int)c,(void *)d,(socklen_t *)e)
#define SetSockopt(a,b,c,d,e)  setsockopt((int)a,(int)b,(int)c,(const void *)d,(int)e)
#define GetHostByName(a)       gethostbyname((const char *)a)
#endif

#endif
