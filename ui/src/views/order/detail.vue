<template>
	<div class="system-menu-container">
		<el-dialog title="订单详情" fullscreen v-model="state.isShowDialog">
			<div class="personal">
				<el-row>
					<el-col :xs="24" :sm="24">
						<el-card shadow="hover">
							<div class="personal-user">
								<div class="personal-user-right">
									<el-row>
										<el-col :span="24" class="personal-title mb18">{{state.ruleForm.orderTitle}} </el-col>
										<el-col :span="24">
											<el-row>
												<el-col :xs="24" :sm="8" class="personal-item mb6">
													<div class="personal-item-label">订单编号：</div>
													<div class="personal-item-value">{{state.ruleForm.orderSn}}</div>
												</el-col>
												<el-col :xs="24" :sm="4" class="personal-item mb6">
													<div class="personal-item-label">订单状态：</div>
													<div class="personal-item-value">{{state.ruleForm.statusStr}}</div>
												</el-col>
												<el-col :xs="24" :sm="4" class="personal-item mb6">
													<div class="personal-item-label">交易状态：</div>
													<div class="personal-item-value">{{state.ruleForm.tradeStatusStr}}</div>
												</el-col>
												<el-col :xs="24" :sm="4" class="personal-item mb6">
													<div class="personal-item-label">支付状态：</div>
													<div class="personal-item-value">{{state.ruleForm.payStatusStr}}</div>
												</el-col>
											</el-row>
										</el-col>
										<el-col :span="24">
											<el-row>
												<el-col :xs="24" :sm="8" class="personal-item mb6">
													<div class="personal-item-label">订单金额：</div>
													<div class="personal-item-value">{{state.ruleForm.orderAmount}}</div>
												</el-col>
												<el-col :xs="24" :sm="4" class="personal-item mb6">
													<div class="personal-item-label">支付金额：</div>
													<div class="personal-item-value">{{state.ruleForm.payAmount}}</div>
												</el-col>
												<el-col :xs="24" :sm="4" class="personal-item mb6">
													<div class="personal-item-label">买家：</div>
													<div class="personal-item-value">{{state.ruleForm.userName}}</div>
												</el-col>
												<el-col :xs="24" :sm="4" class="personal-item mb6">
													<div class="personal-item-label">创建时间：</div>
													<div class="personal-item-value">{{state.ruleForm.created}}</div>
												</el-col>
											</el-row>
										</el-col>
										<el-col :span="24" v-if="state.ruleForm.buyerMsg != null && state.ruleForm.buyerMsg !=''">
											<el-row>
												<el-col :xs="24" :sm="24" class="personal-item mb6">
													<div class="personal-item-label">买家留言：</div>
													<div class="personal-item-value">{{state.ruleForm.buyerMsg}}</div>
												</el-col>
											</el-row>	
										</el-col>
										<el-col :span="24" v-if="state.ruleForm.remarks != null && state.ruleForm.remarks !=''">
											<el-row>
												<el-col :xs="24" :sm="24" class="personal-item mb6">
													<div class="personal-item-label">卖家备注：</div>
													<div class="personal-item-value">{{state.ruleForm.remarks}}</div>
												</el-col>
											</el-row>	
										</el-col>
									</el-row>
								</div>
							</div>
						</el-card>
					</el-col>

					<el-col :span="24">
						<el-card shadow="hover" class="mt15 personal-edit" header="商品信息">
							<div class="personal-edit-safe-box" v-for="(v, k) in state.ruleForm.orderItemList" :key="k">
								<div class="personal-edit-safe-item">
									<div class="personal-edit-safe-item-left">
										<div class="personal-edit-safe-item-left-label">{{ v.productTitle }}</div>
									</div>
									<div class="personal-edit-safe-item-right">
										<el-button type="text">数量 X {{ v.productCount }}</el-button>
									</div>
								</div>
							</div>
						</el-card>
					</el-col>
				</el-row>
			</div>
			<template #footer>
				<span class="dialog-footer">
                    <el-button type="danger" @click="deleteOrder" v-if="state.ruleForm.status ==1 ? true : false">删 除</el-button>
					<el-button @click="onCancel">取 消</el-button>
					<!-- <el-button type="primary" @click="onSubmit" size="small">保 存</el-button> -->
				</span>
			</template>
		</el-dialog>
	</div>
</template>

<script lang="ts" name="orderDetail" setup>
import { reactive, onUpdated } from 'vue';
import { ElMessageBox, ElMessage } from 'element-plus';
import { OrderApi } from '/@/api/order/index';

const emit = defineEmits(["reloadTable"]) 
const orderApi = OrderApi();
// const myRefForm = ref();
const state = reactive({
	isShowDialog: false,
	ruleForm: {
		id: null,
		orderTitle: '',
		orderSn: '',
		statusStr: '',
		tradeStatusStr: '',
		payStatusStr: '',
		orderAmount: '',
		payAmount: '',
		userName: '',
		created: '',
		buyerMsg: '',
		remarks: '',
		orderItemList: [],
		status: 1
	},
	rules: {
		
	},
});
// 打开弹窗
const openDialog = (id?: object) => {
	state.isShowDialog = true;
	state.ruleForm.id=id;
};
// 关闭弹窗
const closeDialog = () => {
	state.isShowDialog = false;
};
// 取消
const onCancel = () => {
	closeDialog();
	initForm();
};

// const onSubmit = () => {

	// myRefForm.value.validate((valid: any) => {
	// 	if (valid) {
			// let params = {
			//     price: state.ruleForm.payAmount,
			// }
			// updateOrder(state.ruleForm.id, params).then(() => {
			// 	closeDialog(); // 关闭弹窗
			// 	// 刷新菜单，未进行后端接口测试
			// 	initForm();
			// 	ctx.emit("reloadTable");
			// }).catch((res) => {
			// 	ElMessage({showClose: true, message: res.message ? res.message : '系统错误' , type: 'error'});
			// })
		// }
	// });

// };

//删除订单
const deleteOrder = () => {
	ElMessageBox.confirm('此操作将删除订单, 是否继续?', '提示', {
		confirmButtonText: '删除',
		cancelButtonText: '取消',
		type: 'warning',
	}).then(() => {
		orderApi.delOrder(state.ruleForm.id).then(() => {
			closeDialog(); // 关闭弹窗
			ElMessage.success("删除成功");
			emit("reloadTable");
		}).catch((res) => {
			ElMessage.error(res.message);
		});
	})
	.catch(() => {});
}

const getOrder = () => {
	if(state.ruleForm.id && state.ruleForm.id != null) {
		orderApi.getOrderDetail(state.ruleForm.id).then((res) => {
			state.ruleForm = res.data;
		})
	}
}

onUpdated(() => {
	getOrder();
});

// 表单初始化，方法：`resetFields()` 无法使用
const initForm = () => {
	state.ruleForm = {};
};

defineExpose({
	openDialog
})

</script>
<style scoped lang="scss">
// @import '../../../theme/mixins/mixins.scss';
.personal {
	.personal-user {
		height: 130px;
		display: flex;
		align-items: center;
		.personal-user-left {
			width: 100px;
			height: 130px;
			border-radius: 3px;
			::v-deep(.el-upload) {
				height: 100%;
			}
			.personal-user-left-upload {
				img {
					width: 100%;
					height: 100%;
					border-radius: 3px;
				}
				&:hover {
					img {
						animation: logoAnimation 0.3s ease-in-out;
					}
				}
			}
		}
		.personal-user-right {
			flex: 1;
			padding: 0 15px;
			.personal-title {
				font-size: 18px;
				// @include text-ellipsis(1);
			}
			.personal-item {
				display: flex;
				align-items: center;
				font-size: 13px;
				.personal-item-label {
					color: var(--el-text-color-secondary);
					// @include text-ellipsis(1);
				}
				.personal-item-value {
					// @include text-ellipsis(1);
				}
			}
		}
	}
	.personal-info {
		.personal-info-more {
			float: right;
			color: var(--el-text-color-secondary);
			font-size: 13px;
			&:hover {
				color: var(--color-primary);
				cursor: pointer;
			}
		}
		.personal-info-box {
			height: 130px;
			overflow: hidden;
			.personal-info-ul {
				list-style: none;
				.personal-info-li {
					font-size: 13px;
					padding-bottom: 10px;
					.personal-info-li-title {
						display: inline-block;
						// @include text-ellipsis(1);
						color: var(--el-text-color-secondary);
						text-decoration: none;
					}
					& a:hover {
						color: var(--color-primary);
						cursor: pointer;
					}
				}
			}
		}
	}
	.personal-recommend-row {
		.personal-recommend-col {
			.personal-recommend {
				position: relative;
				height: 100px;
				color: var(--color-whites);
				border-radius: 3px;
				overflow: hidden;
				cursor: pointer;
				&:hover {
					i {
						right: 0px !important;
						bottom: 0px !important;
						transition: all ease 0.3s;
					}
				}
				i {
					position: absolute;
					right: -10px;
					bottom: -10px;
					font-size: 70px;
					transform: rotate(-30deg);
					transition: all ease 0.3s;
				}
				.personal-recommend-auto {
					padding: 15px;
					position: absolute;
					left: 0;
					top: 5%;
					.personal-recommend-msg {
						font-size: 12px;
						margin-top: 10px;
					}
				}
			}
		}
	}
	.personal-edit {
		.personal-edit-title {
			position: relative;
			padding-left: 10px;
			color: var(--el-text-color-regular);
			&::after {
				content: '';
				width: 2px;
				height: 10px;
				position: absolute;
				left: 0;
				top: 50%;
				transform: translateY(-50%);
				background: var(--color-primary);
			}
		}
		.personal-edit-safe-box {
			border-bottom: 1px solid var(--el-border-color-light, #ebeef5);
			padding: 5px 0;
			.personal-edit-safe-item {
				width: 100%;
				display: flex;
				align-items: center;
				justify-content: space-between;
				.personal-edit-safe-item-left {
					flex: 1;
					overflow: hidden;
					.personal-edit-safe-item-left-label {
						color: var(--el-text-color-regular);
						margin-bottom: 5px;
					}
					.personal-edit-safe-item-left-value {
						color: var(--el-text-color-secondary);
						// @include text-ellipsis(1);
						margin-right: 15px;
					}
				}
			}
			&:last-of-type {
				padding-bottom: 0;
				border-bottom: none;
			}
		}
	}
}
</style>