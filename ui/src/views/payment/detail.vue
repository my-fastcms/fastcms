<template>
	<div >
		<el-dialog title="支付记录详情" fullscreen v-model="state.isShowDialog">
			<div class="personal">
				<el-row>
					<el-col :xs="24" :sm="24">
						<el-card shadow="hover">
							<div class="personal-user">
								<div class="personal-user-right">
									<el-row>
										<el-col :span="24" class="personal-title mb18">{{state.ruleForm.prodTitle}} </el-col>
										<el-col :span="24">
											<el-row>
												<el-col :xs="24" :sm="8" class="personal-item mb6">
													<div class="personal-item-label">支付编号：</div>
													<div class="personal-item-value">{{state.ruleForm.trxNo}}</div>
												</el-col>
												<el-col :xs="24" :sm="8" class="personal-item mb6">
													<div class="personal-item-label">支付状态：</div>
													<div class="personal-item-value">{{state.ruleForm.payStatusStr}}</div>
												</el-col>
												<el-col :xs="24" :sm="8" class="personal-item mb6">
													<div class="personal-item-label">支付时间：</div>
													<div class="personal-item-value">{{state.ruleForm.paySuccessTime}}</div>
												</el-col>
											</el-row>
										</el-col>
										<el-col :span="24">
											<el-row>
												<el-col :xs="24" :sm="8" class="personal-item mb6">
													<div class="personal-item-label">订单金额：</div>
													<div class="personal-item-value">{{state.ruleForm.payAmount}}</div>
												</el-col>
												<el-col :xs="24" :sm="8" class="personal-item mb6">
													<div class="personal-item-label">支付金额：</div>
													<div class="personal-item-value">{{state.ruleForm.paySuccessAmount}}</div>
												</el-col>
												<el-col :xs="24" :sm="8" class="personal-item mb6">
													<div class="personal-item-label">买家：</div>
													<div class="personal-item-value">{{state.ruleForm.nickName}}</div>
												</el-col>
											</el-row>
										</el-col>
										<el-col :span="24">
											<el-row>
												<el-col :xs="24" :sm="24" class="personal-item mb6">
													<div class="personal-item-label">支付类型：</div>
													<div class="personal-item-value">{{state.ruleForm.thirdpartyType}}</div>
												</el-col>
											</el-row>
										</el-col>

										<el-col :span="24">
											<el-row>
												<el-col :xs="24" :sm="24" class="personal-item mb6">
													<div class="personal-item-label">第三方支付appId：</div>
													<div class="personal-item-value">{{state.ruleForm.thirdpartyAppid}}</div>
												</el-col>
											</el-row>
										</el-col>	

										<el-col :span="24">
											<el-row>
												<el-col :xs="24" :sm="24" class="personal-item mb6">
													<div class="personal-item-label">第三方支付openId：</div>
													<div class="personal-item-value">{{state.ruleForm.thirdpartyUserOpenid}}</div>
												</el-col>
											</el-row>
										</el-col>

										<el-col :span="24">
											<el-row>
												<el-col :xs="24" :sm="24" class="personal-item mb6">
													<div class="personal-item-label">第三方支付交易流水号：</div>
													<div class="personal-item-value">{{state.ruleForm.thirdpartyTransactionId}}</div>
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
										<el-col :span="24" v-if="state.ruleForm.remark != null && state.ruleForm.remark !=''">
											<el-row>
												<el-col :xs="24" :sm="24" class="personal-item mb6">
													<div class="personal-item-label">备注：</div>
													<div class="personal-item-value">{{state.ruleForm.remark}}</div>
												</el-col>
											</el-row>	
										</el-col>
									</el-row>
								</div>
							</div>
						</el-card>
					</el-col>
				</el-row>
			</div>
			<template #footer>
				<span class="dialog-footer">
                    <el-button @click="onCancel" size="small">取 消</el-button>
					<!-- <el-button type="primary" @click="onSubmit" size="small">保 存</el-button> -->
				</span>
			</template>
		</el-dialog>
	</div>
</template>

<script lang="ts" name="paymentRecordDetail" setup>
import { reactive, onUpdated } from 'vue';
import { OrderApi } from '/@/api/order/index';

const orderApi = OrderApi();
const state = reactive({
	isShowDialog: false,
	ruleForm: {
		id: null,
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

const getOrder = () => {
	if(state.ruleForm.id && state.ruleForm.id != null) {
		orderApi.getPaymentDetail(state.ruleForm.id).then((res) => {
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
		height: 330px;
		display: flex;
		align-items: center;
		.personal-user-left {
			width: 100px;
			height: 330px;
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
			height: 330px;
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