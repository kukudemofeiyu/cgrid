export type StationCommunityType = {
  thirdSiteId?: string;
};

export type CommunityType = {
  thirdSiteId?: string;
  anyGnSn?: string;
};

export type MeterCommunityType<T = Record<string, any>, D = Record<string, any>> = {
  password: string;
  userName: string;
  powerRatio: string;
  energyRatio: string;
  currentRatio: string;
  voltageRatio: string;
  associateIds?: string;
  associateDevices: T;
  bindList?: D;
};

export type TreeDataType = {
  deviceName: string;
  deviceSN: string;
  id: string;
  parentId: string;
  children: TreeDataType[];
  selectFlag: boolean;
  productId: number;
};
