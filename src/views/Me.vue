<template>
    <reversed-table left-width="25%" class="mt-4">
        <div class="avatar-container" title="编辑头像" @click="$refs.avatarUploader.call()">
            <img :src="avatarSrc" class="avatar" ref="avatar" />
            <div class="avatar-middle">
                <i class="bi-pencil-square"></i>
            </div>
        </div>
    </reversed-table>
    <avatar-uploader ref="avatarUploader" @crop-done="saveAvatar"></avatar-uploader>
</template>

<script>
import { mapActions } from 'vuex';
import ReversedTable from "../components/reversed-table/DataTable.vue";
import Row from "../components/reversed-table/Row.vue";
import AvatarUploader from "../components/Transaction/AvatarUploader.vue";
import Auth from '../utils/Auth';
export default {
    data() {
        return {
            avatarSrc: import.meta.env.VITE_API_URL + '/avatar/' + Auth.getLoggedUser().username
        }
    },
    methods: {
        ...mapActions({ requestUpdateAvatar: "Me/requestUpdateAvatar" }),
        saveAvatar(newAvatar) {
            this.$refs.avatar.src = URL.createObjectURL(newAvatar);
            this.requestUpdateAvatar(newAvatar);
        }
    },
    components: {
        ReversedTable,
        Row,
        AvatarUploader
    }
}
</script>

<style lang="scss" scoped>
.avatar-container {
    position: relative;
    cursor: pointer;

    .avatar {
        padding: 0;
        width: 260px;
        height: 260px;
        border-radius: 100%;
        border: 2px rgb(235, 235, 235) solid;
        transition: 0.5s ease;
        @media (max-width: 1168px) {
            width: 180px;
            height: 180px;
        }
        @media (max-width: 768px) {
            width: 100px;
            height: 100px;
        }
    }

    &:hover {
        .avatar {
            opacity: 0.3;
        }

        .avatar-middle {
            opacity: 1;
        }
    }

    .avatar-middle {
        transition: 0.5s ease;
        position: absolute;
        top: 50%;
        left: 50%;
        opacity: 0;
        transform: translate(60%, 60%);
        text-align: center;
        i {
            font-size: 2.5rem;
        }
        @media (max-width: 1168px) {
            transform: translate(50%, 50%);
            i {
                font-size: 2rem;
            }
        }
        @media (max-width: 768px) {
            transform: translate(20%, 20%);
            i {
                font-size: 1.5rem;
            }
        }
    }
}
</style>