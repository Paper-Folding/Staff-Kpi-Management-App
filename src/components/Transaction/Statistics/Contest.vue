<template>
    <div class="container mb-3">
        <div class="row">
            <div class="col-4">
                <canvas ref="type"></canvas>
            </div>
            <div class="col-4"></div>
            <div class="col-4">
                <canvas ref="level"></canvas>
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
            typeChart: null,
            typeConfig: {
                type: "pie",
                data: {
                    labels: [],
                    datasets: [{
                        label: "char",
                        data: [],
                        backgroundColor: ['#36a2eb', '#ff9f40']
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
                            text: '类别对比',
                            font: {
                                size: 20,
                                weight: 'normal',
                            },
                        }
                    }
                }
            },
            levelChart: null,
            levelConfig: {
                type: "pie",
                data: {
                    labels: [],
                    datasets: [{
                        label: "char2",
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
                            text: '级别统计',
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
        await this.requestContestStatistics();
        this.typeConfig.data.labels = this.contest.type.map(ele => ele.type);
        this.typeConfig.data.datasets[0].data = this.contest.type.map(ele => ele.amount);
        this.typeChart = shallowRef(new Chart(this.$refs.type, this.typeConfig));
        this.levelConfig.data.labels = this.contest.level.map(ele => ele.level);
        this.levelConfig.data.datasets[0].data = this.contest.level.map(ele => ele.amount);
        this.levelChart = shallowRef(new Chart(this.$refs.level, this.levelConfig));
    },
    methods: {
        ...mapActions({ requestContestStatistics: "Statistics/requestContestStatistics" }),
        async reload() {
            await this.requestContestStatistics();
            this.typeChart.data.lebels = this.contest.type.map(ele => ele.type);
            this.typeChart.data.datasets[0].data = this.contest.type.map(ele => ele.amount);
            this.typeChart.update();

            this.levelChart.data.labels = this.contest.level.map(ele => ele.level);
            this.levelChart.data.datasets[0].data = this.contest.level.map(ele => ele.amount);
            this.levelChart.update();
        }
    },
    computed: {
        ...mapGetters({ contest: "Statistics/getContest" })
    }
}
</script>