
import React, { memo, useCallback, useContext, useEffect, useState } from 'react';
import DeviceContext from '../../Context/DeviceContext';
import styles from './index.less';
import {  useRequest } from 'umi';
import { timeRangeTable, timeZoneTable } from './config';
import { DateRangeListType, DateRangeType, RateSettingType, TimeRangeType } from './type';
import { ProTable } from '@ant-design/pro-components';
import TimeSettingModal from './TimeSettingTableModal/TimeSettingTableModal';
import { getMeterRateData } from './service';
import { OnlineStatusEnum } from '@/utils/dictionary';
import { formatMessage } from '@/utils';


const RateSetting: React.FC = (props) => {
    const { data: deviceData } = useContext(DeviceContext);;
    const [realData, setRealData] = useState<RateSettingType>();
    const {passEditAuth, networkStatus} = props;
    const {
        run
    } = useRequest(getMeterRateData, {
        manual: true,
    });
    
    const getData =  useCallback(() => {
         run({
            'deviceId': deviceData?.deviceId,
            'key': 'm33'
        })?.then((res) => {
            var value : RateSettingType = {m34: [], m35: []};
            if(res?.data) {
                value = JSON.parse(res?.data ?? '');
            } 
            var data = dealRealData(value);
            setRealData(data);
        })
    }, [deviceData]);

    useEffect(() => {
        getData();
    }, [deviceData]);

    const dealRealData = (data?:RateSettingType): RateSettingType => {
        let dateRangeList: DateRangeType[] = data?.m34 ?? [];
        let timeRangeList: DateRangeListType[] = data?.m35 ?? [];
        let newTimeRangeList: DateRangeListType[] =  [];
        let originLength = dateRangeList.length;
        dateRangeList = [...dateRangeList, ...Array.from({ length: 14 - originLength }, (_, i) => (
            originLength == 0 && i == 0
                ?
                {
                    Tn: 1,
                    Md: '01-01',
                }
                : {
                    Tn: undefined,
                    Md: undefined
                }

        ))];
        originLength = timeRangeList.length;
        for (let i = 0; i < 8; i++) {
            let timeTable:TimeRangeType[]  ;
           let timeTablePolishLength = 14;
           if( i<originLength) {
            timeTable = timeRangeList[i].m36 ?? [];
           } else {
            timeTable =[]
           }
           for (let j = timeTable.length; j < timeTablePolishLength; j++) {
                timeTable.push( {
                    m37: j == 0 ? 1 : undefined,
                    Hm: j == 0 ? '00:00' : undefined,
                });
           }
           newTimeRangeList.push({
            Tn:i+1,
            m36:timeTable
           });
        }
    
        const updateData:RateSettingType = {m34:dateRangeList,m35:newTimeRangeList };
        return updateData;

    }

   const handleSuccess = (success:Boolean)=> {
        if(success) {
            getData();
        }
    }

    return   realData ? (
        
        <>
         <TimeSettingModal  data={realData} onSuccess={handleSuccess} deviceData={deviceData} disabled={!(passEditAuth && networkStatus === OnlineStatusEnum.Online  )}/>
            <div className={styles.tableList}>
               <div>
               <span className={styles.tableName}>
                {formatMessage({ id: 'device.1050', defaultMessage: '时区表' })}
                </span>
                <ProTable
                    className={styles.table}
                    search={false}
                    pagination={false}
                    options={false}
                    columns={timeZoneTable}
                    bordered
                    dataSource={realData?.m34}
                    size='small'
                ></ProTable>
               </div>

                <div className={styles.tableRangeList}>
                    {realData?.['m35']?.map((item, index) => (

                       <div key={`m35-${index}`}>
                        <span className={styles.tableName}>{formatMessage({ id: 'device.1051', defaultMessage: '时段表' }, { index: index + 1 })}</span>
                         <ProTable
                            className={styles.table}
                            search={false}
                            pagination={false}
                            options={false}
                            bordered
                            columns={timeRangeTable}
                            dataSource={realData?.m35[index]?.m36}
                            size='small'
                        ></ProTable>
                       </div>

                    ))}
                </div>
            </div>
        </>
    ) : (<></>);
    

}


export default memo(RateSetting);