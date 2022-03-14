import { createStore } from 'vuex';
import Login from "./modules/Login.js";

export default createStore({
    state() {
        return {
            // this root state is used to show a notification everywhere
            notify: () => { }
        }
    },
    modules: {
        Login
    }
})
