/** Just a template, delete me once project done developing. */
import request from "../../utils/Ajax";

const state = {

}

const getters = {

}

const actions = {
    async requestUpdateAvatar({ rootState }, newAvatarFile) {
        let formData = new FormData();
        formData.append('avatar', newAvatarFile);
        let res = await request('post', '/updateAvatar', formData, true, {
            'Content-Type': 'multipart/form-data'
        });
        if (res.status === 200 && res.data.code === 200) {
            rootState.notify('头像已更改', 'success');
        } else {
            rootState.notify('头像未成功更改');
        }
    }
}

const mutations = {

}

export default {
    namespaced: true,
    state,
    getters,
    actions,
    mutations
}