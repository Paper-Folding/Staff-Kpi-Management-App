import { createStore } from 'vuex';
import Login from "./modules/Login.js";
import Menu from "./modules/Menu.js";
import Role from './modules/UserAndRole/Role.js';

export default createStore({
    state() {
        return {
            // this root state is used to show a notification everywhere
            notify: () => { }
        }
    },
    modules: {
        Login,
        Menu,
        Role
    }
})
