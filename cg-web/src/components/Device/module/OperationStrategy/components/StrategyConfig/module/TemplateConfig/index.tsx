import React, { useState, useCallback, useEffect } from 'react';
import { Radio, Space, Button, Empty, Modal, message } from 'antd';
import SchemaForm from '@/components/SchemaForm';
import type { TimeEnum } from '../../config';
import { defaultValues, templateColumns, timeRangeHanlder } from '../../config';
import { formatMessage, getUniqueNumber } from '@/utils';
import styles from '../../index.less';
import { cloneDeep } from 'lodash';
import { useRequest } from 'umi';
import { templateList, deleteTemplate, addTemplate, updataTemplate } from '../../service';
import { paramsTemplateList } from '../../../../service';
import { FormTypeEnum } from '@/components/SchemaForm';

type TemplateConfigProps = {
  deviceId?: string | number;
  timeType: TimeEnum;
  passEditAuth?: boolean;
};

const TemplateConfig: React.FC<TemplateConfigProps> = (props) => {
  const { deviceId, timeType, passEditAuth } = props;
  const [initialValues, setInitialValues] = useState<any>(defaultValues);
  const [templateId, setTemplateId] = useState<string | number>();
  const [localTemplateData, setLocalTemplateData] = useState<any>([]);
  const [formType, setFormType] = useState<FormTypeEnum>(FormTypeEnum.Add);
  const [options, setOptions] = useState<any[]>([]);

  const { data: templatData, run } = useRequest(templateList, {
    manual: true,
  });

  const { data: paramsTemplatData, run: runParamsTemplate } = useRequest(paramsTemplateList, {
    manual: true,
  });

  const onTemplatChange = useCallback(
    (e: any) => {
      const value = e.target.value;
      const allTemplatData = templatData?.concat(localTemplateData) || [];
      const curInitialValues = allTemplatData?.find?.((item: any) => item.id === value);
      setInitialValues(timeRangeHanlder(curInitialValues));
      setTemplateId(value);
    },
    [localTemplateData, templatData],
  );

  useEffect(() => {
    if (Number.isFinite(templateId)) {
      setFormType(FormTypeEnum.Edit);
    } else {
      setFormType(FormTypeEnum.Add);
    }
  }, [templateId]);

  useEffect(() => {
    run({ deviceId, dimension: timeType });
  }, [deviceId, run, timeType]);

  useEffect(() => {
    runParamsTemplate({ deviceId });
  }, [deviceId, runParamsTemplate]);

  useEffect(() => {
    const allTemplatData = templatData?.concat(localTemplateData) || [];
    if (allTemplatData.length) {
      const curOptions = allTemplatData?.map?.((item: any) => {
        return {
          label: item.name,
          value: item.id,
        };
      });
      setOptions(curOptions);
      if (localTemplateData.length) {
        const length = localTemplateData.length;
        setInitialValues(timeRangeHanlder(localTemplateData[length - 1]));
        setTemplateId(localTemplateData[length - 1].id);
      } else {
        setInitialValues(timeRangeHanlder(allTemplatData[0]));
        setTemplateId(allTemplatData[0].id);
      }
    } else {
      setOptions([]);
      setInitialValues(timeRangeHanlder(defaultValues));
      setTemplateId(undefined);
    }
  }, [localTemplateData, templatData]);

  const addTemplateFn = useCallback(() => {
    setFormType(FormTypeEnum.Add);
    const curLocalTemplateData = cloneDeep(localTemplateData);
    const curDefaultValues = cloneDeep(defaultValues);
    const length = templatData.length + curLocalTemplateData.length;
    curDefaultValues.name += length;
    curDefaultValues.id += getUniqueNumber(3);
    curLocalTemplateData.push(curDefaultValues);
    setLocalTemplateData(curLocalTemplateData);
  }, [localTemplateData, templatData]);

  const deleteTemplateFn = useCallback(() => {
    if (Number.isFinite(templateId)) {
      Modal.confirm({
        title: formatMessage({ id: 'common.delete', defaultMessage: '删除' }),
        content: formatMessage({
          id: 'dataManage.1094',
          defaultMessage: '你确定要删除吗？删除之后无法恢复！',
        }),
        okText: formatMessage({ id: 'common.confirm', defaultMessage: '确认' }),
        cancelText: formatMessage({ id: 'common.cancel', defaultMessage: '取消' }),
        onOk: () => {
          deleteTemplate({ id: templateId }).then((res: any) => {
            if (res.code == 200) {
              message.success(formatMessage({ id: 'common.del', defaultMessage: '删除成功' }));
              run({ deviceId, dimension: timeType });
            }
          });
        },
      });
    } else {
      const curLocalTemplateData = cloneDeep(localTemplateData);
      const index = curLocalTemplateData.findIndex((item: any) => item.id === templateId);
      curLocalTemplateData.splice(index, 1);
      setLocalTemplateData(curLocalTemplateData);
    }
  }, [deviceId, localTemplateData, run, templateId, timeType]);

  const onBeforeSubmit = useCallback(
    (data) => {
      data.deviceId = deviceId;
      data.dimension = timeType;
      if (formType === FormTypeEnum.Edit) {
        data.id = templateId;
      }
      return timeRangeHanlder(data, 'put');
    },
    [deviceId, formType, templateId, timeType],
  );

  const onSuccess = useCallback(() => {
    run({ deviceId, dimension: timeType });
    setLocalTemplateData([]);
  }, [deviceId, run, timeType]);
  return (
    <>
      <Space>
        <Radio.Group
          onChange={onTemplatChange}
          value={templateId}
          className={styles.templateSelect}
          optionType="button"
        >
          {options.map((item) => (
            <Radio.Button key={item.value} value={item.value}>
              <span title={item.label}>{item.label}</span>
            </Radio.Button>
          ))}
        </Radio.Group>
        <Button type="dashed" onClick={addTemplateFn} disabled={!passEditAuth}>
          {formatMessage({ id: 'device.1095', defaultMessage: '添加模版' })}
        </Button>
      </Space>
      <div className="mt24">
        {templateId ? (
          <SchemaForm
            layoutType="Form"
            grid={true}
            autoFocusFirstInput={false}
            type={formType}
            addData={addTemplate}
            editData={updataTemplate}
            columns={templateColumns(paramsTemplatData)}
            initialValues={initialValues}
            beforeSubmit={onBeforeSubmit}
            onSuccess={onSuccess}
            submitter={{
              render: (_: any, doms: any) => {
                return passEditAuth
                  ? [
                      doms[1],
                      <Button color="primary" danger key="delete" onClick={deleteTemplateFn}>
                        {formatMessage({ id: 'common.delete', defaultMessage: '删除' })}
                      </Button>,
                    ]
                  : [];
              },
            }}
          />
        ) : (
          <Empty className={styles.empty} />
        )}
      </div>
    </>
  );
};

export default TemplateConfig;
