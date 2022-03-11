<template>
    <main class="min-vh-100 py-3 py-md-0">
        <div class="container-lg">
            <div class="card login-card">
                <div class="row g-0">
                    <div class="col-md-5 position-relative">
                        <img src="../assets/images/login-bg.jpg" alt="login-background" class="login-card-img" />
                    </div>
                    <div class="col-md-7 position-relative">
                        <div class="card-body">
                            <header class="h1">员工业绩管理系统</header>
                            <p class="description h1">请登录</p>
                            <div class="ms-xl-5 me-xl-5 ps-xl-5 pe-xl-5">
                                <h5 class="text-danger" v-if="$route.query.msg === 'expired'">您的登录会话已过期，请尝试重新登录！</h5>
                                <div class="form-group">
                                    <label for="loginId" class="visually-hidden">您的用户名</label>
                                    <input type="text" v-model="mobile" class="form-control" :class="mobileIncorrect ? 'is-invalid' : mobile.length === 11 ? 'is-valid' : ''" placeholder="您的用户名" />
                                    <div class="invalid-feedback text-start mb-3">无效的用户名</div>
                                    <div class="valid-feedback text-start mb-3">手机号格式正确</div>
                                </div>
                                <div class="form-group mt-4 mb-4">
                                    <label for="loginPassword" class="visually-hidden">登录密码</label>
                                    <input type="password" v-model="loginPassword" @keyup.enter="handleLogin" :class="passwordIncorrect ? 'is-invalid' : ''" class="form-control" placeholder="登录密码" />
                                    <div class="invalid-feedback text-start mb-3">用户名或密码错误</div>
                                </div>
                                <div class="form-check d-flex justify-content-center mb-4">
                                    <input class="form-check-input" v-model="remembered" type="checkbox" id="check-remember-phone" />&nbsp;
                                    <label class="form-check-label" for="check-remember-phone">记住手机号</label>
                                </div>
                                <div class="d-grid">
                                    <button @click="handleLogin" :disabled="logging" class="btn login-btn mb-4">
                                        <span v-if="!logging">登录后台</span>
                                        <span v-if="logging" class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>
                                        <span v-if="logging">&nbsp;正在登录...</span>
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>
</template>

<script>
export default {
    name: "Login",
    data() {
        return {
            mobile: "",
            loginPassword: "",
            mobileIncorrect: false,
            passwordIncorrect: false,
            remembered: false,
            logging: false,
        };
    },
};
</script>

<style lang="scss" scoped>
main {
    display: flex;
    align-items: center;

    .login-card {
        border: 0;
        border-radius: 27.5px;
        box-shadow: 0 10px 30px 0 rgba(172, 168, 168, 0.43);
        overflow: hidden;

        img {
            border-radius: 0;
            position: absolute;
            width: 100%;
            height: 100%;
            object-fit: cover;
        }

        .card-body {
            padding: 85px 60px 60px;
            text-align: center;

            @media (max-width: 422px) {
                padding: 35px 24px;
            }

            > header {
                margin-bottom: 19px;
            }

            > .description {
                font-size: 25px;
                color: #000;
                font-weight: normal;
                margin-bottom: 23px;
            }
        }

        .form-control {
            border: 1px solid #d5dae2;
            padding: 15px 25px;
            min-height: 45px;
            font-size: 14px;
            line-height: 1.5;
            font-weight: normal;

            &::placeholder {
                color: #919aa3;
            }
        }
        .login-btn {
            padding: 13px 20px 12px;
            background-color: #000;
            border-radius: 4px;
            font-size: 18px;
            font-weight: bold;
            line-height: 20px;
            color: #fff;
            margin-bottom: 24px;
            &:hover {
                border: 1px solid #000;
                background-color: transparent;
                color: #000;
            }
            &:focus {
                box-shadow: unset;
            }
        }
    }
}
</style>