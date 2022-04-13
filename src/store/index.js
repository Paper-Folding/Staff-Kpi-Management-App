import { createStore } from 'vuex';
import Util from './modules/Util.js';
import Login from "./modules/Login.js";
import Navbar from './modules/Navbar.js';
import Menu from "./modules/Menu.js";
import Role from './modules/UserAndRole/Role.js';
import User from './modules/UserAndRole/User.js';
import Me from './modules/Me.js';
import Contest from './modules/Contest.js';
import Statistics from './modules/Statistics.js';

export default createStore({
    state() {
        return {
            // this root state is used to show a notification everywhere
            notify: () => { }
        }
    },
    modules: {
        Util,
        Login,
        Navbar,
        Menu,
        Role,
        User,
        Me,
        Contest,
        Statistics
    }
})
