<template>
	<div class="list-adapt-container">
		<el-card shadow="hover">
			<el-table :data="tableData.data" stripe style="width: 100%">
				<el-table-column prop="id" label="ID" show-overflow-tooltip></el-table-column>
				<el-table-column prop="orderSn" label="订单编号" show-overflow-tooltip></el-table-column>
				<el-table-column prop="title" label="商品" show-overflow-tooltip></el-table-column>
				<el-table-column prop="totalAmount" label="金额" show-overflow-tooltip></el-table-column>
				<el-table-column prop="nickName" label="买家" show-overflow-tooltip></el-table-column>
				<el-table-column prop="statusStr" label="状态" show-overflow-tooltip></el-table-column>
				<el-table-column prop="created" label="创建时间" show-overflow-tooltip></el-table-column>
				<el-table-column prop="path" label="操作" width="90">
					<template #default="scope">
						<el-button size="mini" type="text" @click="onRowInfo(scope.row)">详情</el-button>
					</template>
				</el-table-column>
			</el-table>
			<el-pagination
				style="text-align: right"
				background
				@size-change="onHandleSizeChange"
				@current-change="onHandleCurrentChange"
				:page-sizes="[10, 20, 30]"
				:current-page="tableData.param.pageNum"
				:page-size="tableData.param.pageSize"
				layout="total, sizes, prev, pager, next, jumper"
				:total="tableData.total"
			></el-pagination>
		</el-card>
		<Detail ref="detailRef" @reloadTable="initTableData" />
	</div>
</template>

<script lang="ts">
import { toRefs, ref, reactive, onMounted } from 'vue';
import { getOrderList } from '/@/api/order/client';
import Detail from '/@/views/center/order/component/detail.vue';
export default {
	name: 'orderManager',
	components: { Detail },
	setup() {
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
				},
			},
		});

		const initTableData = () => {
			getOrderList(state.tableData.param).then((res) => {
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
		const onTableItemClick = (v: object) => {
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
		return {
			detailRef,
			initTableData,
			onRowInfo,
			onTableItemClick,
			onHandleSizeChange,
			onHandleCurrentChange,
			...toRefs(state),
		};
	},
};
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
	clear: both;
}
</style>
