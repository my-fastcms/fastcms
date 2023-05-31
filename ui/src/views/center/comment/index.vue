<template>
	<div>
		<el-card shadow="hover">
			<div class="mb15">
				<el-input placeholder="请输入评论内容" size="default" prefix-icon="el-icon-search" style="max-width: 180px" class="ml10"></el-input>
				<el-button type="primary" class="ml10" size="default">查询</el-button>
			</div>
			<el-table :data="state.tableData.data" stripe style="width: 100%">
				<el-table-column prop="id" label="ID" show-overflow-tooltip></el-table-column>
				<el-table-column prop="content" label="评论内容" show-overflow-tooltip></el-table-column>
				<el-table-column prop="parentComment" label="回复评论" show-overflow-tooltip></el-table-column>
				<el-table-column prop="author" label="评论人" show-overflow-tooltip></el-table-column>
				<el-table-column prop="articleTitle" label="评论文章" show-overflow-tooltip></el-table-column>
				<el-table-column prop="status" label="状态" show-overflow-tooltip></el-table-column>
				<el-table-column prop="created" label="创建时间" show-overflow-tooltip></el-table-column>
			</el-table>
			<el-pagination
				@size-change="onHandleSizeChange"
				@current-change="onHandleCurrentChange"
				class="mt15"
				:pager-count="5"
				:page-sizes="[10, 20, 30]"
				v-model:current-page="state.tableData.param.pageNum"
				background
				v-model:page-size="state.tableData.param.pageSize"
				layout="total, sizes, prev, pager, next, jumper"
				:total="state.tableData.total"
			>
			</el-pagination>
		</el-card>
	</div>
</template>

<script lang="ts" name="articleCommentManager" setup>
import { reactive, onMounted } from 'vue';
import { ClientArticleApi } from '/@/api/article/client';

const clientArticleApi = ClientArticleApi();
const state = reactive({
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

// 初始化表格数据
const initTableData = () => {
	clientArticleApi.getArticleCommentList(state.tableData.param).then((res) => {
		state.tableData.data = res.data.records;
		state.tableData.total = res.data.total;
	}).catch(() => {
	})
};

// 分页改变
const onHandleSizeChange = (val: number) => {
	state.tableData.param.pageSize = val;
	initTableData();
};
// 分页改变
const onHandleCurrentChange = (val: number) => {
	state.tableData.param.pageNum = val;
	initTableData();
};

// 页面加载时
onMounted(() => {
	initTableData();
});
</script>
