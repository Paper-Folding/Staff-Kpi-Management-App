<template>
    <div class="container mb-3">
        <div class="row">
            <div class="col-4">
                <canvas ref="gender"></canvas>
            </div>
            <div class="col-4"></div>
            <div class="col-4">
                <canvas ref="age"></canvas>
            </div>
        </div>
    </div>
</template>

<script>
import Chart from "chart.js/auto";
import { mapActions, mapGetters } from 'vuex';
import { shallowRef } from 'vue';
export default {
    data() {
        return {
            genderChart: null,
            genderConfig: {
                type: "pie",
                data: {
                    labels: [],
                    datasets: [{
                        label: "chart",
                        data: [],
                        backgroundColor: ['#36a2eb', '#ff6384', '#727272']
                    }]
                },
                options: {
                    responsive: true,
                    plugins: {
                        legend: {
                            position: 'top',
                        },
                        title: {
                            display: true,
                            text: '性别统计',
                            font: {
                                size: 20,
                                weight: 'normal',
                            },
                        }
                    }
                }
            },
            ageChart: null,
            ageConfig: {
                type: "pie",
                data: {
                    labels: [],
                    datasets: [{
                        label: "chart2",
                        data: [],
                        backgroundColor: ['#4bbfc0', '#36a2eb', '#ff6384', '#ff9f40', '#fecd56', '#e00']
                    }]
                },
                options: {
                    responsive: true,
                    plugins: {
                        legend: {
                            position: 'top',
                        },
                        title: {
                            display: true,
                            text: '年龄统计',
                            font: {
                                size: 20,
                                weight: 'normal',
                            },
                        }
                    }
                }
            }
        }
    },
    async mounted() {
        await this.requestStaffStatistics();
        this.genderConfig.data.labels = this.staff.gender.map(ele => ele.gender);
        this.genderConfig.data.datasets[0].data = this.staff.gender.map(ele => ele.amount);
        this.genderChart = shallowRef(new Chart(this.$refs.gender, this.genderConfig));
        this.ageConfig.data.labels = this.staff.age.map(ele => ele.ranges);
        this.ageConfig.data.datasets[0].data = this.staff.age.map(ele => ele.amount);
        this.ageChart = shallowRef(new Chart(this.$refs.age, this.ageConfig));
    },
    methods: {
        ...mapActions({ requestStaffStatistics: "Statistics/requestStaffStatistics" })
    },
    computed: {
        ...mapGetters({ staff: "Statistics/getStaff" })
    }
}
</script>