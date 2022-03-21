import axios from "axios";
import Auth from "./Auth";

/**
 * Make ajax requests.
 * @param method method to request, pass string like 'post', 'get'
 * @param url url to request, you do not need to pass in baseUrl. (e.g. '/getUser')
 * @param dataOrParams if you use 'get' method, it is params, else is data. Why? Because 'get' does not support to pass request body to back end.
 * @param isAuthrequired true for pass in auth header false otherwise; default is true
 * @param headers headers to pass, if you just want to pass in authorization header, set isAuthrequired to true is ok
 * @param timeout default is 5000(ms)
 */
const request = (method, url, dataOrParams, isAuthrequired = true, headers = {}, timeout = 5000) => {
    return axios.request({
        method: method,
        baseURL: import.meta.env.VITE_API_URL,
        url: url,
        data: method.toLowerCase !== 'get' ? dataOrParams : undefined,
        params: method.toLowerCase === 'get' ? dataOrParams : undefined,
        headers: isAuthrequired ? { ...Auth.header(), ...headers } : headers,
        timeout: timeout
    });
}

export default request;