<template>
    <main class="min-vh-100 py-3 py-md-0">
        <div class="container-lg">
            <div class="card login-card">
                <div class="row g-0" style="transition: all 1s ease ">
                    <div class="col-md-5 position-relative">
                        <img src="../assets/images/login-bg.jpg" alt="login-background" class="login-card-img" />
                    </div>
                    <div class="col-md-7 position-relative">
                        <div class="card-body">
                            <header class="h3 text-start">信息工程学院</header>
                            <header class="h1">员工业绩管理系统</header>
                            <p class="description h4">请登录</p>
                            <div class="ms-xl-5 me-xl-5 ps-xl-5 pe-xl-5">
                                <h5 class="text-danger" v-if="$route.query.msg === 'expired'">登录会话已然过期， 请重新登录~</h5>
                                <h5 class="text-danger" v-if="validateFailed">用户名或密码有误，请核对并重试~</h5>
                                <div class="form-group">
                                    <input type="text" v-model="username" class="form-control" :class="validUsername ? null : 'is-invalid'" placeholder="您的用户名" />
                                    <div class="invalid-feedback text-start mb-3 ms-3">无效的用户名</div>
                                </div>
                                <div class="form-group mt-4 mb-3">
                                    <input type="password" v-model="password" @keyup.enter="handleLogin" :class="validPassword ? null : 'is-invalid'" class="form-control" placeholder="登录密码" />
                                    <div class="invalid-feedback text-start mb-3 ms-3">无效的密码</div>
                                </div>
                                <div class="form-check d-flex justify-content-center mb-3">
                                    <input class="form-check-input" v-model="remembered" type="checkbox" id="check-remember" />&nbsp;
                                    <label class="form-check-label" for="check-remember">记住用户名</label>
                                </div>
                                <div class="d-grid mb-4">
                                    <Button :disabled="logging" @click="handleLogin" class="login-btn">
                                        <span v-if="!logging">登入</span>
                                        <template v-if="logging">
                                            <div class="spinner-grow spinner-size-adjust" role="status">
                                                <span class="visually-hidden">绫绫!</span>
                                            </div>
                                            <span v-if="logging">&nbsp;登入中...</span>
                                        </template>
                                    </Button>
                                </div>
                                <div class="bottom-info">Powered by Vite and Vue.js</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>
</template>

<script>
import { mapActions } from "vuex";
import Button from "../components/SkewFillButton.vue";
export default {
    name: "Login",
    data() {
        return {
            username: "",
            password: "",
            validUsername: true,
            validPassword: true,
            validateFailed: false,
            remembered: false,
            logging: false,
        };
    },
    methods: {
        async handleLogin() {
            this.validateFailed = false;
            if (this.ifStrUnSafe(this.username))
                this.validUsername = false;
            else
                this.validUsername = true
            if (this.ifStrUnSafe(this.password))
                this.validPassword = false;
            else
                this.validPassword = true;
            if (!this.validPassword || !this.validUsername) {
                return;
            }

            this.logging = true;
            await setTimeout(() => { }, 1000)
            await this.login({
                username: this.username,
                password: this.password
            });
            if (this.$store.state.Login.logined) {
                this.$router.push("/dashboard");
            } else {
                this.validateFailed = true;
                this.logging = false;
            }
        },
        ...mapActions({ login: "Login/postLogin" })
    },
    created() {
        this.ifStrUnSafe = str => str === '' || /[' ','\'','"','-',',',';']/.test(str);
    },
    mounted() {
        document.body.classList.add('login');
    },
    unmounted() {
        document.body.classList.remove('login');
    },
    components: {
        Button
    }
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
                font-weight: normal;
                margin-bottom: 23px;
            }

            .form-group {
                input {
                    border-radius: 10em;
                    font-size: 1.05em;
                    border: 2px solid #d4d4d4;
                    padding: 0.6em 1.5em;
                    line-height: 1.5;
                    transition: border 0.3s ease, box-shadow 0.3s ease;

                    &::placeholder {
                        color: #a0a0a0;
                    }

                    &:hover {
                        border-color: #000;

                        &::placeholder {
                            color: rgb(105, 105, 105);
                        }
                    }

                    &:focus {
                        border-color: #000;
                        box-shadow: 0 0 0 0.25rem rgba(0, 0, 0, 0.25);
                    }

                    &.is-invalid {
                        border-color: #e00;

                        &:focus {
                            box-shadow: 0 0 0 0.25rem rgba(238, 0, 0, 0.25);
                        }
                    }
                }
            }

            .form-check {
                > * {
                    cursor: pointer;
                    user-select: none;
                }

                > input {
                    &:focus {
                        box-shadow: 0 0 0 0.25rem rgba(0, 0, 0, 0.25);
                        border-color: rgba(0, 0, 0, 0.25);
                    }

                    &:checked {
                        background-color: #000;
                        border-color: #000;
                    }
                }
            }

            .spinner-size-adjust {
                width: 1.2rem;
                height: 1.2rem;
            }

            .bottom-info {
                color: rgb(114, 114, 114);
                letter-spacing: 1px;
                font-size: 1.1em;
            }
        }

        .login-btn {
            ::v-deep() {
                --skew-btn-default-bg-color: #fff;
                --skew-btn-hover-bg-color: #000;
                --skew-btn-border: 2px solid #000;
                --skew-btn-padding: 0.3em 0;
            }
            font-weight: bold;
            letter-spacing: 0.2em;
            vertical-align: middle;
        }
    }
}
</style>