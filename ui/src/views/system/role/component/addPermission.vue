<template>
	<div class="system-menu-container">
		<el-dialog title="权限设置" fullscreen v-model="isShowDialog" width="769px">
			<el-tree :data="treeData" show-checkbox node-key="id" ref="treeTable" :props="treeDefaultProps" @check="onCheckTree">
             </el-tree>
			<template #footer>
				<span class="dialog-footer">
					<el-button @click="onCancel" size="small">取 消</el-button>
					<el-button type="primary" @click="onSubmit" size="small">保 存</el-button>
				</span>
			</template>
		</el-dialog>
	</div>
</template>

<script lang="ts">
import { reactive, toRefs, getCurrentInstance, onBeforeMount } from 'vue';
import { ElMessage } from 'element-plus';

export default {
	name: 'pagesTree',
	setup() {
		const { proxy } = getCurrentInstance() as any;
		const state = reactive({
			isShowDialog: false,
			treeCheckAll: false,
			treeLoading: false,
			treeData: [],
			treeDefaultProps: {
				children: 'children',
				label: 'label',
			},
			treeSelArr: [],
			treeLength: 0,
		});
		// 打开弹窗
		const openDialog = (roleId?: string) => {
			console.log("roleId:" + roleId);
			state.isShowDialog = true;
		};
		// 关闭弹窗
		const closeDialog = (row?: object) => {
			console.log(row);
			state.isShowDialog = false;
		};
		// 取消
		const onCancel = () => {
			closeDialog();
		};

		const initTreeLengh = (arr: any) => {
			let count = 0;
			arr.map((item) => {
				if (item.children) {
					count += item.children.length;
				}
			});
			state.treeLength = count + arr.length;
		};
		// 全选改变时
		const onCheckAllChange = () => {
			if (state.treeCheckAll) {
				proxy.$refs.treeTable.setCheckedNodes(state.treeData);
			} else {
				proxy.$refs.treeTable.setCheckedKeys([]);
			}
		};
		// 节点选中状态发生变化时的回调
		const onCheckTree = () => {
			state.treeSelArr = [];
			state.treeSelArr = proxy.$refs.treeTable.getCheckedNodes();
			state.treeSelArr.length == state.treeLength ? (state.treeCheckAll = true) : (state.treeCheckAll = false);
		};
		// 选择元素按钮
		const onSelect = () => {
			let treeArr = proxy.$refs.treeTable.getCheckedNodes();
			if (treeArr.length <= 0) {
				ElMessage.warning('请选择元素');
				return;
			} else {
				console.log(proxy.$refs.treeTable.getCheckedNodes());
			}
		};
		// 初始化树模拟数据
		const getTreeData = () => {
			state.treeData = [
				{
					id: 1,
					label: '12987121',
					label1: '好滋好味鸡蛋仔',
					label2: '荷兰优质淡奶，奶香浓而不腻',
					isShow: true,
					children: [
						{
							id: 11,
							label: '一级 1-1',
							label1: '好滋好味鸡蛋仔',
							label2: '荷兰优质淡奶，奶香浓而不腻',
						},
						{
							id: 12,
							label: '一级 1-2',
						},
					],
				},
				{
					id: 2,
					label: '12987122',
					label1: '好滋好味鸡蛋仔',
					label2: '荷兰优质淡奶，奶香浓而不腻',
					isShow: true,
					children: [
						{
							id: 21,
							label: '二级 2-1',
							isShow: false,
						},
						{
							id: 22,
							label: '二级 2-2',
						},
					],
				},
				{
					id: 3,
					label: '12987123',
					label1: '好滋好味鸡蛋仔',
					label2: '荷兰优质淡奶，奶香浓而不腻',
					isShow: true,
					children: [
						{
							id: 31,
							label: '二级 3-1',
						},
						{
							id: 32,
							label: '二级 3-2',
						},
						{
							id: 33,
							label: '二级 3-3',
						},
					],
				},
			];
			initTreeLengh(state.treeData);
		};
		// 页面加载前
		onBeforeMount(() => {
			getTreeData();
		});

		//提交数据
		const onSubmit = () => {

		}

		return {
			openDialog,
			closeDialog,
			onCancel,
			onSubmit,
			getTreeData,
			onCheckAllChange,
			onCheckTree,
			onSelect,
			...toRefs(state),
		};
	},
};
</script>
