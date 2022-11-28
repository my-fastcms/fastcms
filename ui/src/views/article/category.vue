<template>
	<div class="system-Category-container">
		<el-card shadow="hover">
			<el-button @click="onOpenAddCategory" class="mt15" size="small" type="primary" icon="iconfont icon-shuxingtu">新建分类</el-button>
			<el-table :data="menuTableData" stripe style="width: 100%" row-key="id" :tree-props="{ children: 'children', hasChildren: 'hasChildren' }">
				<el-table-column prop="title" label="名称" show-overflow-tooltip>
					<template #default="scope">
					<a :href="scope.row.url" target="_blank">
						{{scope.row.title}}
					</a></template>
				</el-table-column>
				<el-table-column prop="id" label="id" show-overflow-tooltip></el-table-column>
				<el-table-column prop="path" label="路径" show-overflow-tooltip></el-table-column>
				<el-table-column prop="suffix" label="模板" show-overflow-tooltip></el-table-column>
				<el-table-column prop="sortNum" label="排序" show-overflow-tooltip></el-table-column>
				<el-table-column prop="created" label="创建时间" show-overflow-tooltip></el-table-column>
				<el-table-column label="操作" show-overflow-tooltip width="125">
					<template #default="scope">
						<el-button size="mini" type="text" @click="onOpenAddCategory(scope.row)">新增</el-button>
						<el-button size="mini" type="text" @click="onOpenEditCategory(scope.row)">修改</el-button>
						<el-button size="mini" type="text" @click="onTabelRowDel(scope.row)">删除</el-button>
					</template>
				</el-table-column>
			</el-table>
		</el-card>
		<AddCategory ref="addCategoryRef" @reloadTable="loadCategoryList"/>
		<EditCategory ref="editCategoryRef" @reloadTable="loadCategoryList"/>
	</div>
</template>

<script lang="ts">
import { ref, toRefs, reactive, computed, onMounted } from 'vue';
import { ElMessageBox, ElMessage } from 'element-plus';
import { getArticleCategoryList, delArticleCategory } from '/@/api/article/index';
import AddCategory from '/@/views/article/component/addCategory.vue';
import EditCategory from '/@/views/article/component/editCategory.vue';

export default {
	name: 'articleCategory',
	components: { AddCategory, EditCategory },
	setup() {
		const addCategoryRef = ref();
		const editCategoryRef = ref();
		const state = reactive({
			menuData: null
		});
		// 获取 vuex 中的路由
		const menuTableData = computed(() => {
			return state.menuData;
		});
		// 打开新增菜单弹窗
		const onOpenAddCategory = (row: object) => {
			addCategoryRef.value.openDialog(row);
		};
		// 打开编辑菜单弹窗
		const onOpenEditCategory = (row: object) => {
			editCategoryRef.value.openDialog(row);
		};
		// 删除当前行
		const onTabelRowDel = (row: any) => {
			ElMessageBox.confirm('此操作将永久删除分类, 是否继续?', '提示', {
				confirmButtonText: '删除',
				cancelButtonText: '取消',
				type: 'warning',
			}).then(() => {
				console.log(row);
				delArticleCategory(row.id).then(() => {
					ElMessage.success("删除成功");
					loadCategoryList();
				}).catch((res) => {
					ElMessage.error(res.message);
				});
			}).catch(()=> {})
		};

		const loadCategoryList = () => {
			getArticleCategoryList().then((res) => {
				state.menuData = res.data;
			}).catch(() => {
			})
		}

		onMounted(() => {
			loadCategoryList();
		});

		return {
			addCategoryRef,
			editCategoryRef,
			onOpenAddCategory,
			onOpenEditCategory,
			menuTableData,
			onTabelRowDel,
			loadCategoryList,
			...toRefs(state),
		};
	},
};
</script>
