package com.g7s.ics.base.enums;


import java.util.Objects;

/**
 * 保险类别枚举
 *
 * @author yzp
 * @since 2020-07-31
 */
public enum InsuranceTypeEnum {
    /**
     * 保险类别枚举
     */



    COMPULSORY_TRAFFIC_INSURANCE(1, "交强险"),
    CAR_DAMAGE_INSURANCE(2, "机动车辆损失险"),
    THIRD_PARTY_LIABILITY_INSURANCE(3, "第三者责任险"),

    //THIRD_PARTY_LIABILITY_INSURANCE(3, "第三者责任险", Lists.newArrayList(
   //         10, 15, 20, 30, 50, 100, 150
    //), 150),
    DRIVER_LIABILITY_INSURANCE(4, "司机责任险"),
    PASSENGER_LIABILITY_INSURANCE(5, "乘客责任险"),
    NATURAL_LOSS_INSURANCE(6, "自然损失险"),
    NO_DEDUCTIBLE(7, "不计免赔");

    private final Integer code;
    private final String desc;
    /**
     * 金额配置列表（单位：万元）
     */
    //private final List<Integer> moneyConfigList;
   // private final Integer defaultMoney;

    InsuranceTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
        //this.moneyConfigList = moneyConfigList;
        //this.defaultMoney = defaultMoney;
    }

    public static InsuranceTypeEnum getObjectEnumByCode(Integer code) {
        for (InsuranceTypeEnum belongRegionEnum : InsuranceTypeEnum.values()) {
            if (Objects.equals(belongRegionEnum.getCode(), code)) {
                return belongRegionEnum;
            }
        }
        return null;
    }

    public static InsuranceTypeEnum getObjectEnumByDesc(String desc) {
        for (InsuranceTypeEnum belongRegionEnum : InsuranceTypeEnum.values()) {
            if (Objects.equals(belongRegionEnum.getDesc(), desc)) {
                return belongRegionEnum;
            }
        }
        return null;
    }

    public static boolean isValid(Integer code) {
        for (InsuranceTypeEnum g : InsuranceTypeEnum.values()) {
            if (g.getCode().equals(code)) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }


    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

//    public List<Integer> getMoneyConfigList() {
//        return moneyConfigList;
//    }
//
//    public Integer getDefaultMoney() {
//        return defaultMoney;
//    }
}
