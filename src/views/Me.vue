<template>
    <div class="mt-4 w-100">
        <div class="row">
            <div class="col-3">
                <div class="avatar-container" title="更换头像" @click="$refs.avatarUploader.call()">
                    <VMenu
                        :triggers="['focus']"
                        :showTriggers="triggers => [...triggers, 'hover', 'click']"
                        :hideTriggers="triggers => [...triggers, 'click']"
                        :skidding="0"
                    >
                        <img :src="avatarSrc" class="avatar" ref="avatar" />
                        <div class="avatar-middle">
                            <i class="bi-pencil-square"></i>
                        </div>
                        <template #popper>
                            <div class="p-2 d-flex gap-2">
                                <button class="btn btn-light" @click="$refs.avatarUploader.call()">
                                    <i class="bi-image-alt"></i>
                                    更改头像
                                </button>
                                <button class="btn btn-dark" @click="restoreAvatar">
                                    <i class="bi-arrow-clockwise"></i>
                                    还原默认
                                </button>
                            </div>
                        </template>
                    </VMenu>
                </div>
            </div>
            <div class="col-9">
                <h1>您的个人信息(肆意编辑)</h1>
                <reversed-table left-width="25%">
                    <Row :row="{ type: 'str', left: '工号', right: me.no }"></Row>
                    <Row :row="{ left: '姓名' }">
                        <label-input
                            v-model="me.name"
                            placeholder="填写姓名"
                            @on-confirm="requestUpdateMeInfo({ name: me.name })"
                        ></label-input>
                    </Row>
                    <Row :row="{ left: '性别' }">
                        <label-select
                            v-model="me.gender"
                            :list="[{ text: '男', value: '男' }, { text: '女', value: '女' }]"
                            @on-confirm="requestUpdateMeInfo({ gender: me.gender })"
                        ></label-select>
                    </Row>
                    <Row :row="{ left: '民族' }">
                        <label-input
                            v-model="me.nation"
                            placeholder="填写民族"
                            @on-confirm="requestUpdateMeInfo({ nation: me.nation })"
                        ></label-input>
                    </Row>
                    <Row :row="{ left: '出生年月日' }">
                        <label-date-picker
                            type="date"
                            v-model="me.birth"
                            @on-confirm="requestUpdateMeInfo({ birth: maid.formatDate(me.birth, 'YYYY-MM-DD') })"
                        ></label-date-picker>
                    </Row>
                    <Row :row="{ left: '参加工作时间' }">
                        <label-date-picker
                            type="date"
                            v-model="me.enrollTime"
                            @on-confirm="requestUpdateMeInfo({ enroll_time: maid.formatDate(me.enrollTime, 'YYYY-MM-DD') })"
                        ></label-date-picker>
                    </Row>
                    <Row :row="{ left: '政治面貌' }">
                        <label-select
                            v-model="me.politic"
                            :list="[{ text: '群众', value: '群众' }, { text: '共青团员', value: '共青团员' }, { text: '中共党员', value: '中共党员' }, { text: '共青团员', value: '共青团员' }, { text: '其他', value: '其他' }]"
                            @on-confirm="requestUpdateMeInfo({ politic: me.politic })"
                        ></label-select>
                    </Row>
                    <Row :row="{ left: '专业' }">
                        <label-input
                            v-model="me.major"
                            placeholder="填写专业"
                            @on-confirm="requestUpdateMeInfo({ major: me.major })"
                        ></label-input>
                    </Row>
                    <Row :row="{ left: '学历学位' }">
                        <label-input
                            v-model="me.level"
                            placeholder="填写学历学位"
                            @on-confirm="requestUpdateMeInfo({ level: me.level })"
                        ></label-input>
                    </Row>
                    <Row :row="{ left: '学历学位授予单位' }">
                        <label-input
                            v-model="me.levelUnit"
                            placeholder="填写学历学位授予单位"
                            @on-confirm="requestUpdateMeInfo({ level_unit: me.levelUnit })"
                        ></label-input>
                    </Row>
                    <Row :row="{ left: '学历学位授予日期' }">
                        <label-date-picker
                            type="date"
                            v-model="me.levelDate"
                            @on-confirm="requestUpdateMeInfo({ level_date: maid.formatDate(me.levelDate, 'YYYY-MM-DD') })"
                        ></label-date-picker>
                    </Row>
                    <Row :row="{ left: '职称' }">
                        <label-input
                            v-model="me.jobAlias"
                            placeholder="填写职称"
                            @on-confirm="requestUpdateMeInfo({ job_alias: me.jobAlias })"
                        ></label-input>
                    </Row>
                    <Row :row="{ left: '主要研究方向' }">
                        <label-input
                            v-model="me.researchDirection"
                            placeholder="填写主要研究方向"
                            @on-confirm="requestUpdateMeInfo({ research_direction: me.researchDirection })"
                        ></label-input>
                    </Row>
                    <Row :row="{ left: '职务' }">
                        <label-input
                            v-model="me.job"
                            placeholder="填写职务"
                            @on-confirm="requestUpdateMeInfo({ job: me.job })"
                        ></label-input>
                    </Row>
                    <Row :row="{ left: '部门' }">
                        <label-input
                            v-model="me.department"
                            placeholder="填写部门"
                            @on-confirm="requestUpdateMeInfo({ department: me.department })"
                        ></label-input>
                    </Row>
                    <Row :row="{ left: '身份证号' }">
                        <label-input
                            v-model="me.idcard"
                            placeholder="填写身份证号"
                            @on-confirm="requestUpdateMeInfo({ idcard: me.idcard })"
                        ></label-input>
                    </Row>
                    <Row :row="{ left: '办公电话' }">
                        <label-input
                            v-model="me.phone"
                            placeholder="填写办公电话"
                            @on-confirm="requestUpdateMeInfo({ phone: me.phone })"
                        ></label-input>
                    </Row>
                    <Row :row="{ left: '长号' }">
                        <label-input
                            v-model="me.longPhone"
                            placeholder="填写长号"
                            @on-confirm="requestUpdateMeInfo({ long_phone: me.longPhone })"
                        ></label-input>
                    </Row>
                    <Row :row="{ left: '短号' }">
                        <label-input
                            v-model="me.shortPhone"
                            placeholder="填写短号"
                            @on-confirm="requestUpdateMeInfo({ short_phone: me.shortPhone })"
                        ></label-input>
                    </Row>
                </reversed-table>
            </div>
        </div>
    </div>

    <avatar-uploader ref="avatarUploader" @crop-done="saveAvatar"></avatar-uploader>
</template>

<script>
import { Menu as VMenu } from 'floating-vue';
import { mapActions } from 'vuex';

import ReversedTable from "../components/reversed-table/DataTable.vue";
import Row from "../components/reversed-table/Row.vue";
import LabelInput from '../components/LabelInput.vue';
import LabelSelect from '../components/LabelSelect.vue';
import LabelDatePicker from '../components/LabelDatePicker.vue';

import AvatarUploader from "../components/Transaction/AvatarUploader.vue";
import Auth from '../utils/Auth';
import Maid from '../utils/Maid';
export default {
    data() {
        return {
            avatarSrc: import.meta.env.VITE_API_URL + '/me/avatar/' + Auth.getLoggedUser().username,
            me: this.$store.state.Me.meInfoTemplate
        }
    },
    created() {
        this.maid = Maid;
    },
    async mounted() {
        await this.requestMeInfo();
        this.me = this.$store.state.Me.meInfo;
    },
    methods: {
        ...mapActions({ requestUpdateAvatar: "Me/requestUpdateAvatar", requestMeInfo: "Me/requestMeInfo", requestUpdateMeInfo: "Me/requestUpdateMeInfo" }),
        saveAvatar(newAvatar) {
            this.$refs.avatar.src = URL.createObjectURL(newAvatar);
            this.requestUpdateAvatar(newAvatar);
        },
        async restoreAvatar() {
            if (confirm('确实要重置回默认头像吗？')) {
                await this.requestUpdateAvatar(null);
                this.$refs.avatar.src = null;
                this.$refs.avatar.src = this.avatarSrc;
            }
        }
    },
    components: {
        VMenu,
        ReversedTable,
        LabelInput,
        LabelSelect,
        LabelDatePicker,
        Row,
        AvatarUploader
    }
}
</script>

<style lang="scss" scoped>
.avatar-container {
    // @media (min-width: 1280px) {
    //     width: 260px;
    //     height: 260px;
    // }
    position: relative;
    cursor: pointer;

    .avatar {
        padding: 0;
        width: 15vw;
        height: 15vw;
        border-radius: 100%;
        border: 2px rgb(235, 235, 235) solid;
        transition: 0.5s ease;
        // @media (max-width: 1168px) {
        //     width: 180px;
        //     height: 180px;
        // }
        // @media (max-width: 768px) {
        //     width: 100px;
        //     height: 100px;
        // }
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
        transform: translate(80%, 80%);
        text-align: center;
        i {
            font-size: 2.5em;
        }
        @media (max-width: 1168px) {
            transform: translate(140%, 70%);
            i {
                font-size: 2rem;
            }
        }
        @media (max-width: 768px) {
            transform: translate(120%,20%);
            i {
                font-size: 1.5rem;
            }
        }
    }
}
</style>