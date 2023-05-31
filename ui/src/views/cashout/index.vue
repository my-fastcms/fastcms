<template>
	<div class="list-adapt-container">
		<el-card shadow="hover">
			<div class="mb15">
				<el-input v-model="state.tableData.param.userName" placeholder="用户" style="max-width: 180px" class="ml10"></el-input>
				<el-select style="max-width: 180px" v-model="state.tableData.param.status" placeholder="状态" clearable class="ml10">
					<el-option label="待审核" value="0"></el-option>
					<el-option label="审核通过" value="1"></el-option>
					<el-option label="审核拒绝" value="2"></el-option>
				</el-select>
				<el-button type="primary" class="ml10" @click="initTableData"><el-icon><ele-Search /></el-icon>查询</el-button>
			</div>
			<el-table :data="state.tableData.data" stripe style="width: 100%">
				<el-table-column prop="id" label="ID" show-overflow-tooltip width="60"></el-table-column>
				<el-table-column prop="nickName" label="用户" show-overflow-tooltip></el-table-column>
				<el-table-column prop="amount" label="提现金额" show-overflow-tooltip></el-table-column>
				<el-table-column prop="payTo" label="支付账号" show-overflow-tooltip></el-table-column>
				<el-table-column prop="auditTypeStr" label="审核类型" show-overflow-tooltip></el-table-column>
				<el-table-column prop="statusStr" label="状态" show-overflow-tooltip></el-table-column>
				<el-table-column prop="created" label="提现时间" show-overflow-tooltip></el-table-column>
				<el-table-column prop="path" label="操作" width="90">
					<template #default="scope">
						<el-button size="mini" type="text" @click="onRowInfo(scope.row)">详情</el-button>
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

<script lang="ts" setup name="cashOutManager">
import { ref, reactive, onMounted } from 'vue';
import { OrderApi } from '/@/api/order/index';
import Detail from '/@/views/cashout/detail.vue';

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
			userName: '',
			status: 0
		},
	},
});

const initTableData = () => {
	orderApi.getCashoutList(state.tableData.param).then((res) => {
		state.tableData.data = res.data.records;
		state.tableData.total = res.data.total;
	});
};

onMounted(() => {
	initTableData();
});

const onRowInfo = (row: any) => {
	detailRef.value.openDialog(row.id);
};

// 当前列表项点击
const onTableItemClick = (v: any) => {
	detailRef.value.openDialog(v.id);
};
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

defineExpose({
	onTableItemClick
})
</script>

<style scoped lang="scss">
.upload-btn {
	padding-bottom: 10px;
}

.bottom {
    margin-top: 13px;
    line-height: 12px;
}

.button {
	padding: 0;
	float: right;
}

.image {
    width: 100%;
	height: 200px;
    display: block;
}

.clearfix:before,
.clearfix:after {
    display: table;
    content: "";
}
  
.clearfix:after {
	clear: both
}
</style>
