import {makeMockRequest, METHODS} from "@entando/apimanager";
import {
  DASHBOARD_CONFIG_LIST,
  DASHBOARD_LIST_DATASOURCE,
  DATASOURCE_PARKING_DATA
} from "mocks/dashboardConfigs";

export const getServerConfigs = () =>
  makeMockRequest({
    uri: "/api/dashboard/serverConfigs",
    method: METHODS.GET,
    mockResponse: DASHBOARD_CONFIG_LIST,
    useAuthentication: false
  });

export const postServerConfig = serverConfig =>
  makeMockRequest({
    uri: "/api/dashboard/serverConfigs",
    method: METHODS.POST,
    body: serverConfig,
    mockResponse: {...serverConfig},
    useAuthentication: false
  });

export const putServerConfig = serverConfig =>
  makeMockRequest({
    uri: `/api/dashboard/serverConfigs/${serverConfig.id}`,
    method: METHODS.PUT,
    body: serverConfig,
    mockResponse: {...serverConfig},
    useAuthentication: false
  });

export const deleteServerConfig = serverConfigId =>
  makeMockRequest({
    uri: `/api/dashboard/serverConfigs/${serverConfigId}`,
    method: METHODS.DELETE,
    mockResponse: {},
    useAuthentication: false
  });

export const getDatasources = configId =>
  makeMockRequest({
    uri: `/api/dashboard/serverConfig/${configId}/datasources`,
    method: METHODS.GET,
    mockResponse: DASHBOARD_LIST_DATASOURCE[configId],
    useAuthentication: false
  });

export const getDatasourceData = (configId, datasourceId, type) =>
  makeMockRequest({
    uri: `/api/dashboard/serverConfig/${configId}/datasource/${datasourceId}/${type}`,
    method: METHODS.GET,
    mockResponse: DATASOURCE_PARKING_DATA[type],
    useAuthentication: false
  });