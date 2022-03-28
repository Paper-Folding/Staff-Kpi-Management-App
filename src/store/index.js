import { createStore } from 'vuex';
import Login from "./modules/Login.js";
import Navbar from './modules/Navbar.js';
import Menu from "./modules/Menu.js";
import Role from './modules/UserAndRole/Role.js';
import User from './modules/UserAndRole/User.js';

export default createStore({
    state() {
        return {
            // this root state is used to show a notification everywhere
            notify: () => { }
        }
    },
    modules: {
        Login,
        Navbar,
        Menu,
        Role,
        User
    }
})
