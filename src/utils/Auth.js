import jsCookie from "js-cookie";
import router from "../router";

export default class Auth {
    static header() {
        const user = this.getLoggedUser();
        if (user && user.token)
            return { Authorization: 'Bearer ' + user.token };
        else {
            router.push({ path: '/', params: { msg: 'expired' } });
            return {};
        }
    }

    static getLoggedUser() {
        return JSON.parse(jsCookie.get("loggedUser") || "{}");
    }

    static isUserLogined() {
        const user = this.getLoggedUser();
        return user && user.token && user.token.length > 0;
    }
}