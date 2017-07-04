/*
 * controller.h
 *
 *  Created on: 2017Äê5ÔÂ18ÈÕ
 *      Author: Tradoff
 */

#ifndef CONTROLLER_H_
#define CONTROLLER_H_

#define MAX_DEV_NR	3


typedef enum {
	DEVICE_BATHROOM_LIGHT = 0, DEVICE_BEDROOM_LIGHT, DEVICE_KITCHEN_LIGHT,
	DEVICE_CAMERA = 10,
	DEVICE_TEMPERATURE_SENSOR = 20,
	DEVICE_HUMIDITY_SENSOR = 20,
	DEVICE_AIR_CONDITIOINER = 30
} DEVICE_ID;

typedef enum {
	LOCATION_UNDERDOOR = 3000, LOCATION_KITCHEN = 3001, LOCATION_BEDROOM = 3002, LOCATION_BATHROOM = 3003
} LOCATION;
#define LOCATION_DEFAULT	LOCATION_UNDERDOOR

typedef enum {
	TYPE_LIGHT = 2000, TYPE_CAMERA = 2100, TYPE_ELECTRIC_MONITOR = 2200, TYPE_AIR_CONDITIONER = 2300
} TYPE;


#define ACTION_1_NR		5
#define ACTION_2_NR		3

/* normal button */
#define ACTION_CLOSE	1000
#define ACTION_OPEN		1001

/* camera */
#define ACTION_SHOOT	1100
#define ACTION_PICK		1101

/* data controller */
#define ACTION_START	1200
#define ACTION_STOP		1201
#define ACTION_UPLOAD	1202

/* air conditioner */
#define ACTION_SET		1300

/* center controller */
#define ACTION_LIST_DEVICE	1400

#define JSON_ACTION	"action"
#define JSON_OBJECT	"object"

typedef struct Device {
	int id;
	char* name;
	int location;
	int type;
} Device;

const Device *getDevices();
cJSON *deviceToJsonItem(const Device *device);
cJSON *getDeviceListJson();
int handleReq(char **req);
int handlePkg(EdpPacket* pkg);

typedef int handle_ret_t;
typedef int object_t;

#define handlerNull	0
handle_ret_t handlerClose(object_t);
handle_ret_t handlerOpen(object_t);
handle_ret_t handlerShoot(object_t);
handle_ret_t handlerPick(object_t);
handle_ret_t handlerStart(object_t);
handle_ret_t handlerStop(object_t);
handle_ret_t handlerUpload(object_t);
handle_ret_t handlerSet(object_t);
handle_ret_t handlerList(object_t);

handle_ret_t (*g_handler_map[][ACTION_2_NR])(object_t) = {
		{handlerClose,	handlerOpen,	handlerNull		},
		{handlerShoot,	handlerPick,	handlerNull		},
		{handlerStart,	handlerStop,	handlerUpload	},
		{handlerSet,	handlerNull,	handlerNull		},
		{handlerList,	handlerNull,	handlerNull		}
};

//extern int g_sockfd;

/*
const char **location_map = {"bedroom", "bathroom"};
const char **type_map = {"light", "camera", "air conditioner"};*/

#endif /* CONTROLLER_H_ */
