<template>
	<div class="system-Category-container">
		<el-card shadow="hover">
			<el-button @click="onOpenAddTag" size="default" type="primary"><el-icon><ele-Plus /></el-icon>新建标签</el-button>
			<el-table :data="menuTableData" stripe style="width: 100%" row-key="id">
				<el-table-column prop="tagName" label="名称" show-overflow-tooltip>
					<template #default="scope">
					<a :href="scope.row.url" target="_blank">
						{{scope.row.tagName}}
					</a></template>
				</el-table-column>
				<el-table-column prop="id" label="id" show-overflow-tooltip></el-table-column>
				<el-table-column prop="suffix" label="模板" show-overflow-tooltip></el-table-column>
				<el-table-column prop="sortNum" label="排序" show-overflow-tooltip></el-table-column>
				<el-table-column prop="created" label="创建时间" show-overflow-tooltip></el-table-column>
				<el-table-column label="操作" show-overflow-tooltip width="125">
					<template #default="scope">
						<el-button size="small" text type="primary" @click="onOpenEditTag(scope.row)">修改</el-button>
						<el-button size="small" text type="primary" @click="onTabelRowDel(scope.row)">删除</el-button>
					</template>
				</el-table-column>
			</el-table>
		</el-card>
		<AddTag ref="addTagRef" @reloadTable="loadTagList"/>
	</div>
</template>

<script lang="ts" name="articleTag" setup>
import { ref, reactive, computed, onMounted } from 'vue';
import { ElMessageBox, ElMessage } from 'element-plus';
import { ArticleApi } from '/@/api/article/index';
import AddTag from '/@/views/article/component/addTag.vue';

const articleApi = ArticleApi();
const addTagRef = ref();
const state = reactive({
	menuData: null
});
// 获取 vuex 中的路由
const menuTableData = computed(() => {
	return state.menuData;
});
// 打开新增菜单弹窗
const onOpenAddTag = (row: object) => {
	addTagRef.value.openDialog(row);
};
// 打开编辑菜单弹窗
const onOpenEditTag = (row: object) => {
	addTagRef.value.openDialog(row);
};
// 删除当前行
const onTabelRowDel = (row: any) => {
	ElMessageBox.confirm('此操作将永久删除标签, 是否继续?', '提示', {
		confirmButtonText: '删除',
		cancelButtonText: '取消',
		type: 'warning',
	}).then(() => {
		articleApi.delArticleTag(row.id).then(() => {
			ElMessage.success("删除成功");
			loadTagList();
		}).catch((res) => {
			ElMessage.error(res.message);
		});
	}).catch(()=> {})
};

const loadTagList = () => {
	articleApi.getArticleTagList().then((res) => {
		state.menuData = res.data;
	}).catch(() => {
	})
}

onMounted(() => {
	loadTagList();
});
</script>
