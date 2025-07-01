export const enum RenderType {
    table,
    form
}
export type DateRangeType = {
    Tn?: number ,
    Md?: string 
}
export type TimeRangeType = {
    m37?: number ,
    Hm?: string 
}

export type DateRangeListType = {
    Tn: number,
    m36: TimeRangeType[]
}
export type RateSettingType = {
    m34: DateRangeType[],
    m35: DateRangeListType[]
}

export enum TimeTypeEnum {
    Date,
    Time
}

export type MeterDataTYpe = {
    createBy: number

    createTime: string

    data: string

    deviceId: number

    id: number

    sendKey: string

}