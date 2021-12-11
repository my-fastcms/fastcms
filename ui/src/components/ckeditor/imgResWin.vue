<template>
  <el-dialog title="选择附件" fullscreen v-model="isShowDialog">
		<div>
			<el-upload 
				class="upload-btn"
				:action="uploadUrl"
				name="files"
				multiple
				:headers="headers"
				:show-file-list="false"
				:on-success="uploadSuccess"
				:on-exceed="onHandleExceed"
				:on-error="onHandleUploadError"
				:limit="limit">
				<el-button size="small" type="primary">上传附件</el-button>
			</el-upload>
			
			<el-card shadow="hover">
				<div v-if="tableData.data.length > 0">
					<el-row :gutter="15">
						<el-checkbox-group :max="max" v-model="checkedObjs">
							<el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4" class="mb15" v-for="(v, k) in tableData.data" :key="k" @click="onTableItemClick(v)">
								<el-card :body-style="{ padding: '0px' }">
									<img :src="v.path" class="image">
									<div style="padding: 14px;">
										<el-checkbox :label="v"><span>{{ v.fileName }}</span></el-checkbox>
									</div>
								</el-card>
							</el-col>
						</el-checkbox-group>
					</el-row>
				</div>
				<el-empty v-else description="暂无数据"></el-empty>
				<template v-if="tableData.data.length > 0">
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
					>
					</el-pagination>
				</template>
			</el-card>
		</div>
		<template #footer>
				<span class="dialog-footer">
					<el-button @click="closeDialog" size="small">取 消</el-button>
					<el-button type="primary" @click="onSubmit" size="small">确 定</el-button>
				</span>
			</template>
	</el-dialog>
</template>

<script lang="ts">
import {toRefs, ref, reactive, onMounted } from "vue";
import { getAttachList } from '/@/api/attach/index';
import { ElMessage } from 'element-plus';
import { Session } from '/@/utils/storage';
import insertImage from "./imgPlugin/insertImage";
import connect from "./imgPlugin/connect";

export default {
  emits: ["attachHandler"],
	name: 'ckeditorAttachDialog',
  setup(props, ctx) {
    
    const state = reactive({
			isShowDialog: connect.showWinControl,
			queryParams: {},
			showSearch: true,
			max: 1,
			limit: 3,
			uploadUrl: import.meta.env.VITE_API_URL + "/admin/attachment/upload",
			headers: {"Authorization": Session.get('token')},
			checkedObjs: [],	//选中的图片元素
			tableData: {
				data: [],
				total: 99,
				loading: false,
				param: {
					pageNum: 1,
					pageSize: 10,
				},
			},
		});

    let selectedIndex = ref(1);
    let changeSelect = function(index) {
      selectedIndex.value = index;
    }
    let imgList = reactive([]);
    // onMounted(() => {
    //   const mockData = [{
    //     src: "https://img0.baidu.com/it/u=2089938236,2600489549&fm=26&fmt=auto",
    //   },{
    //     src: "https://img2.baidu.com/it/u=2840876380,2046257499&fm=26&fmt=auto",
    //   },{
    //     src: "https://img2.baidu.com/it/u=3745443056,3700782634&fm=26&fmt=auto",
    //   }];
    //   // 模拟请求数据
    //   setTimeout(() => {
    //     imgList.push(...mockData);
    //   }, 2000);
    // });
    const confirm = function() {
      insertImage(connect.editorObj.model, imgList[selectedIndex.value]);
      //emit("update:show", false);
    }    

		const openDialog = (max) => {
			state.isShowDialog = true;
			state.max = max;
		};
		// 关闭弹窗
		const closeDialog = () => {
			state.isShowDialog = false;
			state.max = 1;
		};

		const initTableData = () => {
			getAttachList().then((res) => {
				state.tableData.data = res.data.records;
				state.tableData.total = res.data.total;
			});
		};

		const uploadSuccess = () => {
			initTableData();
		}

		const onHandleExceed = () => {
			ElMessage.error("上传文件数量不能超过 "+state.limit+" 个!");
		}
		// 上传失败
		const onHandleUploadError = () => {
			ElMessage.error("上传失败");
		}

		onMounted(() => {
			initTableData();
		});

		// 当前列表项点击
		const onTableItemClick = (v: object) => {
			console.log(v);
		};
		// 分页点击
		const onHandleSizeChange = (val: number) => {
			state.tableData.param.pageSize = val;
		};
		// 分页点击
		const onHandleCurrentChange = (val: number) => {
			state.tableData.param.pageNum = val;
		};
		
		const onSubmit = () => {
			//把选中的附件传递给父组件
			ctx.emit("attachHandler", state.checkedObjs);
			closeDialog();
		};

    return {
      openDialog,
			closeDialog,
			initTableData,
			onTableItemClick,
			onHandleSizeChange,
			onHandleCurrentChange,
			onHandleExceed,
			onHandleUploadError,
			uploadSuccess,
			onSubmit,
      selectedIndex,
      changeSelect,
      imgList,
      confirm,
      ...toRefs(state),
    }
  }
};
</script>

<style scoped>
  .imgResWin {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background-color: rgba(0, 0, 0, 0.6);
    z-index: 50;
  }
  .imgResWinCon {
    position: absolute;
    top: 100px;
    left: 100px;
    right: 100px;
    bottom: 100px;
    background-color: #fff;
    padding: 50px;
  }
  .imgResWinConImgs:after {
    content: "";
    display: block;
    clear: both;
  }
  .imgResWinConImgs > div {
    margin: 0 50px 50px 0;
    width: 200px;
    height: 150px;
    cursor: pointer;
    float: left;
  }
  .selected {
    outline: #409eff 10px solid;
  }
  .imgResWinConImgs img {
    height: 100%;
    width: 100%;
  }
  .confirm {
    margin-top: 100px;
    background-color: #409eff;
    font-size: 20px;
    padding: 15px 20px;
    border-radius: 6px;
    text-align: center;
    display: inline-block;
    cursor: pointer;
    color: #fff;
  }
</style>
