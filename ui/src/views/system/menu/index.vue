<template>
	<div class="system-menu-container">
		<el-card shadow="hover">
			<el-button @click="onOpenAddMenu" class="mt15" size="small" type="primary" icon="iconfont icon-shuxingtu">新建路由</el-button>
			<el-table :data="menuTableData" stripe style="width: 100%" row-key="path" :tree-props="{ children: 'children', hasChildren: 'hasChildren' }">
				<el-table-column label="菜单名称" show-overflow-tooltip>
					<template #default="scope">
						<i :class="scope.row.meta.icon"></i>
						<span class="ml10">{{ $t(scope.row.meta.title) }}</span>
					</template>
				</el-table-column>
				<el-table-column prop="name" label="路由名称" show-overflow-tooltip></el-table-column>
				<el-table-column prop="path" label="路由地址" show-overflow-tooltip></el-table-column>
				<el-table-column label="组件地址" show-overflow-tooltip>
					<template #default="scope">
						<span>{{ scope.row.component }}</span>
					</template>
				</el-table-column>
				<el-table-column label="隐藏" show-overflow-tooltip width="70">
					<template #default="scope">
						<span v-if="scope.row.meta.isHide" class="color-primary">是</span>
						<span v-else class="color-info">否</span>
					</template>
				</el-table-column>
				<el-table-column label="缓存" show-overflow-tooltip width="70">
					<template #default="scope">
						<span v-if="scope.row.meta.isKeepAlive" class="color-primary">是</span>
						<span v-else class="color-info">否</span>
					</template>
				</el-table-column>
				<el-table-column label="固定" show-overflow-tooltip width="70">
					<template #default="scope">
						<span v-if="scope.row.meta.isAffix" class="color-primary">是</span>
						<span v-else class="color-info">否</span>
					</template>
				</el-table-column>
				<el-table-column label="外链" show-overflow-tooltip width="70">
					<template #default="scope">
						<span v-if="scope.row.meta.isLink && !scope.row.meta.isIframe" class="color-primary">是</span>
						<span v-else class="color-info">否</span>
					</template>
				</el-table-column>
				<el-table-column label="iframe" show-overflow-tooltip width="70">
					<template #default="scope">
						<span v-if="scope.row.meta.isLink && scope.row.meta.isIframe" class="color-primary">是</span>
						<span v-else class="color-info">否</span>
					</template>
				</el-table-column>
				<el-table-column label="操作" show-overflow-tooltip width="125">
					<template #default="scope">
						<el-button size="mini" type="text" @click="onOpenAddMenu(scope.row)">新增</el-button>
						<el-button size="mini" type="text" @click="onOpenEditMenu(scope.row)">修改</el-button>
						<el-button size="mini" type="text" @click="onTabelRowDel(scope.row)">删除</el-button>
					</template>
				</el-table-column>
			</el-table>
		</el-card>
		<AddMenu ref="addMenuRef" @reloadTable="loadMenuList"/>
		<EditMenu ref="editMenuRef" @reloadTable="loadMenuList"/>
	</div>
</template>

<script lang="ts">
import { ref, toRefs, reactive, computed, onMounted } from 'vue';
import { ElMessageBox, ElMessage } from 'element-plus';
import { getMenuList, delMenu } from '/@/api/menu/index';
import { initBackEndControlRoutes } from '/@/router/backEnd';
import AddMenu from '/@/views/system/menu/component/addMenu.vue';
import EditMenu from '/@/views/system/menu/component/editMenu.vue';

export default {
	name: 'systemMenu',
	components: { AddMenu, EditMenu },
	setup() {
		const addMenuRef = ref();
		const editMenuRef = ref();
		const state = reactive({
			menuData: null
		});
		// 获取 vuex 中的路由
		const menuTableData = computed(() => {
			return state.menuData;
		});
		// 打开新增菜单弹窗
		const onOpenAddMenu = (row: object) => {
			addMenuRef.value.openDialog(row);
		};
		// 打开编辑菜单弹窗
		const onOpenEditMenu = (row: object) => {
			editMenuRef.value.openDialog(row);
		};
		// 删除当前行
		const onTabelRowDel = (row: any) => {
			ElMessageBox.confirm('此操作将永久删除路由, 是否继续?', '提示', {
				confirmButtonText: '删除',
				cancelButtonText: '取消',
				type: 'warning',
			}).then(() => {
				console.log(row);
				delMenu(row.id).then(() => {
					ElMessage.success("删除成功");
					loadMenuList();
					initBackEndControlRoutes();
				}).catch((res) => {
					ElMessage.error(res.message);
				});
			}).catch(()=> {})
		};

		const loadMenuList = () => {
			getMenuList().then((res) => {
				state.menuData = res.data;
			}).catch(() => {
			})
		}

		onMounted(() => {
			loadMenuList();
		});

		return {
			addMenuRef,
			editMenuRef,
			onOpenAddMenu,
			onOpenEditMenu,
			menuTableData,
			onTabelRowDel,
			loadMenuList,
			...toRefs(state),
		};
	},
};
</script>
