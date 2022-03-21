<template>
    <div class="pt-5 pb-5 ps-md-5 pe-md-5 ps-sm-3 pe-sm-3 content">
        <div class="px-4 pt-5 my-5 text-center border-bottom">
            <h2 class="display-5 fw-normal">
                您好
                <span class="fw-bold" id="name-span">{{ username }}</span>🍜，看上去这里是信息工程学院的员工业绩管理系统🍉
            </h2>
            <div class="col-lg-11 mx-auto">
                <h1 class="fs-3 mb-4 mt-5">您可以在👈的菜单中找到您所需要的功能选项</h1>
            </div>
        </div>
    </div>
</template>

<script>
import Auth from "../utils/Auth.js";
export default {
    data() {
        return {
            username: Auth.getLoggedUser().realName
        }
    }
}
</script>

<style>
</style>