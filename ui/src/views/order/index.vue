<template>
	<div class="">
		<el-card shadow="hover">
			<div class="mb15">
				<el-input size="default" v-model="state.tableData.param.orderSn" placeholder="订单编号" style="max-width: 180px" class="ml10"></el-input>
				<el-input size="default" v-model="state.tableData.param.title" placeholder="商品名称" style="max-width: 180px" class="ml10"></el-input>
				<el-select size="default" style="max-width: 180px" v-model="state.tableData.param.payStatus" placeholder="支付状态" clearable class="ml10">
					<el-option label="已支付" :value="1"></el-option>
					<el-option label="未支付" :value="0"></el-option>
				</el-select>
				<el-select size="default" style="max-width: 180px" v-model="state.tableData.param.tradeStatus" placeholder="交易状态" clearable class="ml10">
					<el-option label="交易中" :value="1"></el-option>
					<el-option label="交易完成(可退款)" :value="2"></el-option>
					<el-option label="取消交易" :value="3"></el-option>
					<el-option label="交易完成" :value="8"></el-option>
					<el-option label="订单关闭" :value="9"></el-option>
				</el-select>
				<el-select size="default" style="max-width: 180px" v-model="state.tableData.param.status" placeholder="订单状态" clearable class="ml10">
					<el-option label="正常" :value="1"></el-option>
					<el-option label="删除" :value="0"></el-option>
				</el-select>
				<el-button size="default" type="primary" class="ml10" @click="initTableData"><el-icon><ele-Search /></el-icon>查询</el-button>
			</div>
			<el-table :data="state.tableData.data" stripe style="width: 100%">
				<el-table-column prop="id" label="ID" show-overflow-tooltip></el-table-column>
				<el-table-column prop="orderSn" label="订单编号" show-overflow-tooltip></el-table-column>
				<el-table-column prop="title" label="商品" show-overflow-tooltip></el-table-column>
				<el-table-column prop="totalAmount" label="金额" show-overflow-tooltip></el-table-column>
				<el-table-column prop="userName" label="买家" show-overflow-tooltip></el-table-column>
				<el-table-column prop="payStatusStr" label="支付状态" show-overflow-tooltip></el-table-column>
				<el-table-column prop="created" label="创建时间" show-overflow-tooltip></el-table-column>
				<el-table-column prop="path" label="操作" width="90">
					<template #default="scope">
						<el-button size="small" text type="primary" @click="onRowInfo(scope.row)">详情</el-button>
					</template>
				</el-table-column>
			</el-table>
			<template v-if="state.tableData.data.length > 0">
				<el-pagination
					style="text-align: right"
					background
					@size-change="onHandleSizeChange"
					@current-change="onHandleCurrentChange"
					:page-sizes="[10, 20, 30]"
					:current-page="state.tableData.param.pageNum"
					:page-size="state.tableData.param.pageSize"
					layout="total, sizes, prev, pager, next, jumper"
					:total="state.tableData.total"
				>
				</el-pagination>
			</template>
		</el-card>
		<Detail ref="detailRef" @reloadTable="initTableData"/>
	</div>
</template>

<script lang="ts" setup name="orderManager">
import { ref, reactive, onMounted } from 'vue';
import { OrderApi } from '/@/api/order/index';
import Detail from '/@/views/order/detail.vue';

const orderApi = OrderApi();
const detailRef = ref();
const state = reactive({
	fit: "fill",
	queryParams: {},
	showSearch: true,
	tableData: {
		data: [],
		total: 0,
		loading: false,
		param: {
			pageNum: 1,
			pageSize: 10,
			orderSn: '',
			title: '',
			payStatus: 1,
			tradeStatus: 1,
			status:1
		},
	},
});

const initTableData = () => {
	orderApi.getOrderList(state.tableData.param).then((res) => {
		state.tableData.data = res.data.records;
		state.tableData.total = res.data.total;
	});
};

onMounted(() => {
	initTableData();
});

const onRowInfo = (row: object) => {
	detailRef.value.openDialog(row.id);
};

// 当前列表项点击
// const onTableItemClick = (v: object) => {
// 	detailRef.value.openDialog(v.id);
// };
// 分页点击
const onHandleSizeChange = (val: number) => {
	state.tableData.param.pageSize = val;
	initTableData();
};
// 分页点击
const onHandleCurrentChange = (val: number) => {
	state.tableData.param.pageNum = val;
	initTableData();
};
</script>

<style scoped lang="scss">

</style>
