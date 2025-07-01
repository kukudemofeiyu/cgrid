/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-11-16 09:27:07
 * @LastEditTime: 2023-11-16 16:59:51
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Device\PvEnergyMachine\Overview\EnergyFlow.tsx
 */

import React from 'react';

export type EnergyFlowType = {
  className?: string;
};

const EnergyFlow: React.FC<EnergyFlowType> = (props) => {
  const { className, children } = props;

  return (
    <>
      <svg className={className} width="391px" height="241px" viewBox="0 0 391 241" version="1.1">
        <g stroke="none" strokeWidth="1" fill="none" fillRule="evenodd">
          <g transform="translate(-444.000000, -481.000000)">
            <g transform="translate(212.000000, 102.000000)">
              <g transform="translate(0.000000, 214.000000)">
                <g transform="translate(20.000000, 80.000000)">
                  <g transform="translate(100.000000, 40.000000)">
                    <g transform="translate(112.000000, 45.000000)">
                      <rect x="0" y="0" width="391" height="241" />
                      <path
                        id="path-top-left"
                        d="M5,5.5 L168.963388,5.5 C177.965134,5.50864237 185.283799,12.7176851 185.467855,21.6789518 L185.471386,21.9997328 L185.5,76.0002649 L184.5,76.0002649"
                        fill="none"
                        fillRule="nonzero"
                        stroke="#BCC2C6"
                      />
                      <path
                        id="path-top-right"
                        d="M385,5.5 L385,6.5 L221,6.5 C212.54527,6.5 205.671595,13.2692963 205.503163,21.6836988 L205.5,22 L205.5,76 L204.5,76"
                        fill="none"
                        fillRule="nonzero"
                        stroke="#BCC2C6"
                      />
                      <path
                        id="path-bottom-left"
                        d="M185.5,165.5 L185.5,219.5 C185.5,228.50549 178.285495,235.825942 169.320899,235.996942 L169,236 L5,236 L5,235"
                        fill="none"
                        fillRule="nonzero"
                        stroke="#BCC2C6"
                      />
                      <path
                        id="path-bottom-right"
                        d="M205.5,165.5 L205.5,219.5 C205.5,227.95473 212.269296,234.828405 220.683699,234.996837 L221,235 L385,235 L385,236"
                        fill="none"
                        fillRule="nonzero"
                        stroke="#BCC2C6"
                      />
                      {children}
                    </g>
                  </g>
                </g>
              </g>
            </g>
          </g>
        </g>
      </svg>
    </>
  );
};

export default EnergyFlow;
