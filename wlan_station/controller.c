/*
 * controller.c
 *
 *  Created on: 2017Äê5ÔÂ18ÈÕ
 *      Author: Tradoff
 */
#include "tradoff.h"
#include "cJSON.h"
#include "EdpKit.h"
#include "controller.h"
#include "gpio_if.h"
#include <stdio.h>


const Device g_devices[MAX_DEV_NR] = {
		{DEVICE_BATHROOM_LIGHT, "bathroom light", LOCATION_BATHROOM, TYPE_LIGHT},
		{DEVICE_BEDROOM_LIGHT, "bedroom light", LOCATION_BEDROOM, TYPE_LIGHT},
		{DEVICE_KITCHEN_LIGHT, "kitchen light", LOCATION_KITCHEN, TYPE_LIGHT},
		{DEVICE_TEMPERATURE_SENSOR, "temperature sensor", LOCATION_KITCHEN, TYPE_ELECTRIC_MONITOR},
		{DEVICE_AIR_CONDITIOINER, "bedroom air-conditioner", LOCATION_BEDROOM, TYPE_AIR_CONDITIONER}
};



const Device *getDevices()
{
	return g_devices;
}

cJSON *deviceToJsonItem(const Device *device)
{
	cJSON *json;
	json = cJSON_CreateObject();
	cJSON_AddNumberToObject(json, "ID", device->id);
	cJSON_AddStringToObject(json, "name", device->name);
	cJSON_AddNumberToObject(json, "location", device->location);
	cJSON_AddNumberToObject(json, "type", device->type);
	return json;
}

cJSON *getDeviceListJson()
{
	int i;
	cJSON *json, *array, *body;

	json = cJSON_CreateObject();
	body = cJSON_CreateObject();
	array = cJSON_CreateArray();

    cJSON_AddNumberToObject(json, "action", ACTION_LIST_DEVICE);
	for(i=0; i<MAX_DEV_NR; i++)
	{
		cJSON *dev = deviceToJsonItem(&g_devices[i]);
		if(dev)
		{
			cJSON_AddItemToArray(array, dev);
		}
	}

    cJSON_AddItemToObject(body, "device_list", array);
    cJSON_AddItemToObject(json, "body", body);

    return json;
}

// TODO
handle_ret_t handlerShoot(object_t object){}
handle_ret_t handlerPick(object_t object){}
handle_ret_t handlerStart(object_t object){}
handle_ret_t handlerStop(object_t object){}
handle_ret_t handlerUpload(object_t object){}
handle_ret_t handlerSet(object_t object){}

handle_ret_t handlerOpen(object_t object)
{
	switch(object){
	case DEVICE_KITCHEN_LIGHT:
        GPIO_IF_LedOn(MCU_LIGHT_KITCHEN);
		break;
	case DEVICE_BEDROOM_LIGHT:
        GPIO_IF_LedOn(MCU_LIGHT_BEDROOM);
		break;
	case DEVICE_BATHROOM_LIGHT:
        GPIO_IF_LedOn(MCU_LIGHT_BATHROOM);
		break;
	default:
		break;
	}

	return 0;
}

handle_ret_t handlerClose(object_t object)
{
	switch(object){
	case DEVICE_KITCHEN_LIGHT:
        GPIO_IF_LedOff(MCU_LIGHT_KITCHEN);
		break;
	case DEVICE_BEDROOM_LIGHT:
        GPIO_IF_LedOff(MCU_LIGHT_BEDROOM);
		break;
	case DEVICE_BATHROOM_LIGHT:
        GPIO_IF_LedOff(MCU_LIGHT_BATHROOM);
		break;
	default:
		break;
	}

	return 0;
}

handle_ret_t handlerList(object_t object)
{
	char **cmdid;
	char *resp;
	EdpPacket *pkg;
	handle_ret_t ret;

	cmdid = (char **)object;
	resp = cJSON_Print(getDeviceListJson());
//	printf("response:%s\n", resp);
	pkg = PacketCmdResp(*cmdid, strlen(*cmdid),
	                         resp, strlen(resp));
#ifdef DEBUG
	printf("resp:%s\n", resp);
#endif
	ret = DoSend(g_sockfd, pkg->_data, pkg->_write_pos);
	printf("sended data:%s\n", pkg->_data);

	free(cmdid);
    DeleteBuffer(&pkg);

    return ret;
}


int handleReq(char **req)
{
	cJSON *req_json;
	int action, object;

	req_json = cJSON_Parse(*req);
	action = cJSON_GetObjectItem(req_json, JSON_ACTION)->valueint;
	object = cJSON_GetObjectItem(req_json, JSON_OBJECT)->valueint;
	cJSON_Delete(req_json);

	printf("action: %d, object: %d\n", action, object);

	printf("(action/100) - 10 = %d \n  (action%100) = %d \n", (action/100) - 10, (action%100));

	if((action/100-10)<0||(action/100-10)>=ACTION_1_NR || (action%100)<0||(action%100)>=ACTION_2_NR )
	{
		printf("Error: no action at index (%d, %d)\n", (action/100-10), (action%100));
		return -1;
	}
	g_handler_map[ (action/100) - 10 ][ action % 100 ](object);

	return 0;
}

void add0ToStringTail(char **str, uint32 strlen)
{
	char *temp;
	temp = (char *)malloc((strlen+1)*sizeof(char));
    memcpy(temp, *str, strlen);
    free(*str);
    temp[strlen] = '\0';
    *str = temp;
}

int handlePkg(EdpPacket* pkg)
{
	char **cmdid;
	char **req;
	uint16 cmdid_len;
	uint32 req_len;
	handle_ret_t ret;
	int action;
	cJSON *json;

	cmdid = (char **)malloc(sizeof(char *));
	req = (char **)malloc(sizeof(char *));

	UnpackCmdReq(pkg, cmdid, &cmdid_len, req, &req_len);
	add0ToStringTail(cmdid, cmdid_len);
	add0ToStringTail(req, req_len);

	printf("cmdid:%s with length %u \n\
			req:%s with length %d\n", *cmdid, cmdid_len, *req, req_len);

	json = cJSON_Parse(*req);
	action = cJSON_GetObjectItem(json, "action")->valueint;
	cJSON_Delete(json);

	if(action == ACTION_LIST_DEVICE)
	{
		ret = handlerList(cmdid);
	}
	else
	{
		ret = handleReq(req);
	}

	free(req);
	free(cmdid);

	return ret;
}


