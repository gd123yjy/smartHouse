################################################################################
# Automatically-generated file. Do not edit!
################################################################################

SHELL = cmd.exe

CG_TOOL_ROOT := C:/TI/ccsv6/tools/compiler/arm_15.12.3.LTS

GEN_OPTS__FLAG := --cmd_file="C:/TI/CC3200SDK_1.3.0/cc3200-sdk/ti_rtos/ti_rtos_config/ccs/Default/configPkg/compiler.opt"
GEN_CMDS__FLAG := -l"C:/TI/CC3200SDK_1.3.0/cc3200-sdk/ti_rtos/ti_rtos_config/ccs/Default/configPkg/linker.cmd"

ORDERED_OBJS += \
"./EdpKit.obj" \
"./cJSON.obj" \
"./controller.obj" \
"./gpio_if.obj" \
"./i2c_if.obj" \
"./main.obj" \
"./network_common.obj" \
"./pinmux.obj" \
"./tmp006drv.obj" \
"./tradoff.obj" \
"./uart_if.obj" \
"../cc3200v1p32.cmd" \
$(GEN_CMDS__FLAG) \
-llibc.a \
-l"C:/TI/CC3200SDK_1.3.0/cc3200-sdk/driverlib/ccs/Release/driverlib.a" \
-l"C:/TI/CC3200SDK_1.3.0/cc3200-sdk/simplelink/ccs/OS/simplelink.a" \
-l"C:/TI/CC3200SDK_1.3.0/cc3200-sdk/oslib/ccs/ti_rtos/ti_rtos.a" \

-include ../makefile.init

RM := DEL /F
RMDIR := RMDIR /S/Q

# All of the sources participating in the build are defined here
-include sources.mk
-include subdir_vars.mk
-include subdir_rules.mk
-include objects.mk

ifneq ($(MAKECMDGOALS),clean)
ifneq ($(strip $(S_DEPS)),)
-include $(S_DEPS)
endif
ifneq ($(strip $(S_UPPER_DEPS)),)
-include $(S_UPPER_DEPS)
endif
ifneq ($(strip $(S62_DEPS)),)
-include $(S62_DEPS)
endif
ifneq ($(strip $(C64_DEPS)),)
-include $(C64_DEPS)
endif
ifneq ($(strip $(ASM_DEPS)),)
-include $(ASM_DEPS)
endif
ifneq ($(strip $(CC_DEPS)),)
-include $(CC_DEPS)
endif
ifneq ($(strip $(SV7A_DEPS)),)
-include $(SV7A_DEPS)
endif
ifneq ($(strip $(S55_DEPS)),)
-include $(S55_DEPS)
endif
ifneq ($(strip $(C67_DEPS)),)
-include $(C67_DEPS)
endif
ifneq ($(strip $(CLA_DEPS)),)
-include $(CLA_DEPS)
endif
ifneq ($(strip $(C??_DEPS)),)
-include $(C??_DEPS)
endif
ifneq ($(strip $(CPP_DEPS)),)
-include $(CPP_DEPS)
endif
ifneq ($(strip $(S??_DEPS)),)
-include $(S??_DEPS)
endif
ifneq ($(strip $(C_DEPS)),)
-include $(C_DEPS)
endif
ifneq ($(strip $(C62_DEPS)),)
-include $(C62_DEPS)
endif
ifneq ($(strip $(CXX_DEPS)),)
-include $(CXX_DEPS)
endif
ifneq ($(strip $(C++_DEPS)),)
-include $(C++_DEPS)
endif
ifneq ($(strip $(ASM_UPPER_DEPS)),)
-include $(ASM_UPPER_DEPS)
endif
ifneq ($(strip $(K_DEPS)),)
-include $(K_DEPS)
endif
ifneq ($(strip $(C43_DEPS)),)
-include $(C43_DEPS)
endif
ifneq ($(strip $(INO_DEPS)),)
-include $(INO_DEPS)
endif
ifneq ($(strip $(S67_DEPS)),)
-include $(S67_DEPS)
endif
ifneq ($(strip $(SA_DEPS)),)
-include $(SA_DEPS)
endif
ifneq ($(strip $(S43_DEPS)),)
-include $(S43_DEPS)
endif
ifneq ($(strip $(OPT_DEPS)),)
-include $(OPT_DEPS)
endif
ifneq ($(strip $(PDE_DEPS)),)
-include $(PDE_DEPS)
endif
ifneq ($(strip $(S64_DEPS)),)
-include $(S64_DEPS)
endif
ifneq ($(strip $(C_UPPER_DEPS)),)
-include $(C_UPPER_DEPS)
endif
ifneq ($(strip $(C55_DEPS)),)
-include $(C55_DEPS)
endif
endif

-include ../makefile.defs

# Add inputs and outputs from these tool invocations to the build variables 
EXE_OUTPUTS += \
wlan_station.out \

EXE_OUTPUTS__QUOTED += \
"wlan_station.out" \

BIN_OUTPUTS += \
wlan_station.bin \

BIN_OUTPUTS__QUOTED += \
"wlan_station.bin" \


# All Target
all: wlan_station.out

dependents:
	-cd C:/TI/CC3200SDK_1.3.0/cc3200-sdk/ti_rtos/ti_rtos_config/ccs/Default && $(MAKE) all

# Tool invocations
wlan_station.out: $(OBJS) $(CMD_SRCS) $(GEN_CMDS) C:/TI/CC3200SDK_1.3.0/cc3200-sdk/ti_rtos/ti_rtos_config/ccs/Default/configPkg/linker.cmd
	@echo 'Building target: $@'
	@echo 'Invoking: ARM Linker'
	"C:/TI/ccsv6/tools/compiler/arm_15.12.3.LTS/bin/armcl" -mv7M4 --abi=eabi -me -g --define=ccs --define=_LINUX --define=USE_TIRTOS --define=SL_PLATFORM_MULTI_THREADED --define=cc3200 --diag_wrap=off --diag_warning=225 --display_error_number -z -m"wlan_station.map" --heap_size=0x800 --stack_size=0x800 -i"C:/TI/ccsv6/tools/compiler/arm_15.12.3.LTS/lib" -i"C:/TI/ccsv6/tools/compiler/arm_15.12.3.LTS/include" --reread_libs --display_error_number --diag_wrap=off --warn_sections --xml_link_info="wlan_station_linkInfo.xml" --rom_model -o "wlan_station.out" $(ORDERED_OBJS)
	@echo 'Finished building target: $@'
	@echo ' '
	@$(MAKE) --no-print-directory post-build

wlan_station.bin: $(EXE_OUTPUTS)
	@echo 'Invoking: ARM Hex Utility'
	"C:/TI/ccsv6/tools/compiler/arm_15.12.3.LTS/bin/armhex"  -o "wlan_station.bin" $(EXE_OUTPUTS__QUOTED)
	@echo 'Finished building: $@'
	@echo ' '
	@$(MAKE) --no-print-directory post-build

# Other Targets
clean:
	-$(RM) $(EXE_OUTPUTS__QUOTED)$(BIN_OUTPUTS__QUOTED)
	-$(RM) "EdpKit.d" "cJSON.d" "controller.d" "gpio_if.d" "i2c_if.d" "main.d" "network_common.d" "pinmux.d" "tmp006drv.d" "tradoff.d" "uart_if.d" 
	-$(RM) "EdpKit.obj" "cJSON.obj" "controller.obj" "gpio_if.obj" "i2c_if.obj" "main.obj" "network_common.obj" "pinmux.obj" "tmp006drv.obj" "tradoff.obj" "uart_if.obj" 
	-@echo 'Finished clean'
	-@echo ' '

post-build:
	-"C:/TI/ccsv6/utils/tiobj2bin/tiobj2bin" "wlan_station.out" "wlan_station.bin" "C:/TI/ccsv6/tools/compiler/arm_15.12.3.LTS/bin/armofd" "C:/TI/ccsv6/tools/compiler/arm_15.12.3.LTS/bin/armhex" "C:/TI/ccsv6/utils/tiobj2bin/mkhex4bin"
	-@echo ' '

.PHONY: all clean dependents
.SECONDARY: post-build
C:/TI/CC3200SDK_1.3.0/cc3200-sdk/ti_rtos/ti_rtos_config/ccs/Default/configPkg/linker.cmd:

-include ../makefile.targets

