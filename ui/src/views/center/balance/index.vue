<template>
  <div>
    <el-tabs v-model="activeName" class="demo-tabs" @tab-click="handleClick">
      <el-tab-pane label="资金总览" name="balance">
        <div class="home-container">
          <el-row :gutter="15" class="home-card-one mb15">
            <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="home-media-sm">
              <div class="home-card-item flex">
                <div class="flex-margin flex w100" :class="`home-one-animation`">
                  <div class="flex-auto">
                    <span class="font30">￥{{ userAmount }}</span>
                    <div class="mt10">账户余额</div>
                  </div>
                </div>
              </div>
            </el-col>
          </el-row>
          <el-row :gutter="15" class="home-card-one mb15">
            <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="home-media-sm">
              <el-button type="primary" @click="onCashOutDialog" icon="el-icon-position">提现</el-button>
            </el-col>
          </el-row>
        </div>
      </el-tab-pane>
      <el-tab-pane label="提现记录" name="cashout">
        <div class="mb15">
          <el-select size="small" style="max-width: 180px" v-model="cashoutTableData.param.status" placeholder="状态" clearable class="ml10">
            <el-option label="待审核" value="0"></el-option>
            <el-option label="审核通过" value="1"></el-option>
            <el-option label="审核拒绝" value="2"></el-option>
          </el-select>
          <el-button size="small" type="primary" class="ml10" @click="initCashoutTableData">查询</el-button>
        </div>
        <el-table :data="cashoutTableData.data" stripe style="width: 100%">
          <el-table-column prop="id" label="ID" show-overflow-tooltip></el-table-column>
          <!-- <el-table-column prop="nickName" label="用户" show-overflow-tooltip></el-table-column> -->
          <el-table-column prop="amount" label="提现金额" show-overflow-tooltip></el-table-column>
          <el-table-column prop="payTo" label="支付账号" show-overflow-tooltip></el-table-column>
          <el-table-column prop="auditTypeStr" label="审核类型" show-overflow-tooltip></el-table-column>
          <el-table-column prop="statusStr" label="状态" show-overflow-tooltip></el-table-column>
          <el-table-column prop="created" label="提现时间" show-overflow-tooltip></el-table-column>
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="收入记录" name="amount">
        <el-table :data="incomeTableData.data" stripe style="width: 100%">
          <el-table-column prop="id" label="ID" show-overflow-tooltip></el-table-column>
          <el-table-column prop="created" label="时间" show-overflow-tooltip></el-table-column>
          <el-table-column prop="changeAmount,action" label="金额" show-overflow-tooltip>
            <template #default="scope">
              <span v-if="scope.row.action == 'add'">+</span> 
              <span v-if="scope.row.action == 'del'">-</span> 
              {{scope.row.changeAmount}}
            </template>
          </el-table-column>
          <el-table-column prop="actionDesc" label="描述" show-overflow-tooltip></el-table-column>
        </el-table>
        <template v-if="incomeTableData.data.length > 0">
          <el-pagination
            style="text-align: right"
            background
            @size-change="onHandleSizeChange"
            @current-change="onHandleCurrentChange"
            :page-sizes="[10, 20, 30]"
            :current-page="incomeTableData.param.pageNum"
            :page-size="incomeTableData.param.pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="incomeTableData.total"
          >
          </el-pagination>
        </template>
      </el-tab-pane>
    </el-tabs>

    <el-dialog v-model="dialogFormVisible" title="提现">
      <el-form :model="cashOutForm" size="small" label-width="80px" :rules="cashOutFormRules" ref="myRefCashOutForm">
        <el-row :gutter="35">
          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
            <el-form-item label="提现金额" prop="cashOutAmount">
              <el-input type="number" v-model="cashOutForm.cashOutAmount" placeholder="请输入提现金额" clearable></el-input>
              <span class="mt-2">
                可提现金额：￥{{withInAmount}}
              </span>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="onCashOut">确 定</el-button>
      </span>
      </template>
    </el-dialog>
  </div>
  
</template>
<script lang="ts">
import { toRefs, ref, reactive, onMounted } from 'vue';
import { toRaw } from '@vue/reactivity';
import type { TabsPaneContext } from 'element-plus';
import { getUserAmount, cashoutAmount, getUserCashoutList, getUserIncomeList } from '/@/api/order/client';

export default {
  name: "balanceManager",
  setup() {

    const state = reactive({
			activeName: 'balance',
      dialogFormVisible: false,
      userAmount: 0,
      withInAmount: 0,
      cashOutForm: {
        cashOutAmount: null
      },
      cashOutFormRules: {
        "cashOutAmount": { required: true, message: '请输入提现金额', trigger: 'blur' },
      },
      cashoutTableData: {
        data: [],
				param: {},
			},
      incomeTableData: {
        data: [],
        total: 0,
				loading: false,
				param: {
          pageNum: 1,
					pageSize: 10,
        },
			},
		});

    const handleClick = (tab: TabsPaneContext, event: Event) => {
      console.log(toRaw(toRaw(tab).props).name)
      const currTab = toRaw(toRaw(tab).props).name;
      if (currTab == 'cashout') {
        // 提现记录
        initCashoutTableData()
      } else if (currTab == 'amount') {
        // 收入明细
        initUserIncomeTableData()
      }
    }

    const initCashoutTableData = () => {
      getUserCashoutList(state.cashoutTableData.param).then(res => {
        console.log(res.data)
        state.cashoutTableData.data = res.data;
      })
    }

    const initUserIncomeTableData = () => {
      getUserIncomeList(state.incomeTableData.param).then(res => {
        console.log(res.data)
        state.incomeTableData.data = res.data.records;
        state.incomeTableData.total = res.data.total;
      })
    }

    // 分页点击
		const onHandleSizeChange = (val: number) => {
			state.incomeTableData.param.pageSize = val;
			initUserIncomeTableData();
		};
		// 分页点击
		const onHandleCurrentChange = (val: number) => {
			state.incomeTableData.param.pageNum = val;
			initUserIncomeTableData();
		};

    const onCashOutDialog = () => {
      state.dialogFormVisible = true
    }

    const onCashOut = () => {
      if (state.cashOutForm.cashOutAmount == null) {
        return;
      }
      cashoutAmount(state.cashOutForm.cashOutAmount).then(res => {
        closeDialog()
      })
    }

    // 关闭弹窗
		const closeDialog = () => {
			state.dialogFormVisible = false; 
			state.cashOutForm.cashOutAmount = null;
		}

    onMounted(() => {
			getUserAmount().then(res => {
        if(res.data && res.data != null) {
          state.userAmount = res.data.amount
          state.withInAmount = res.data.withInAmount
        }
      });
		});

    return {
			handleClick,
      onCashOutDialog,
      onCashOut,
      onHandleSizeChange,
      onHandleCurrentChange,
      initCashoutTableData,
      initUserIncomeTableData,
			...toRefs(state),
		};

  }
}

</script>

<style scoped lang="scss">
$homeNavLengh: 8;
.home-container {
	overflow: hidden;
	.home-card-one,
	.home-card-two,
	.home-card-three {
		.home-card-item {
			width: 100%;
			height: 130px;
			border-radius: 4px;
			transition: all ease 0.3s;
			padding: 20px;
			overflow: hidden;
			background: var(--el-color-white);
			color: var(--el-text-color-primary);
			border: 1px solid var(--next-border-color-light);
			&:hover {
				box-shadow: 0 2px 12px var(--next-color-dark-hover);
				transition: all ease 0.3s;
			}
			&-icon {
				width: 70px;
				height: 70px;
				border-radius: 100%;
				flex-shrink: 1;
				i {
					color: var(--el-text-color-placeholder);
				}
			}
			&-title {
				font-size: 15px;
				font-weight: bold;
				height: 30px;
			}
		}
	}
	.home-card-one {
		@for $i from 0 through 3 {
			.home-one-animation#{$i} {
				opacity: 0;
				animation-name: error-num;
				animation-duration: 0.5s;
				animation-fill-mode: forwards;
				animation-delay: calc($i/10) + s;
			}
		}
	}
	.home-card-two,
	.home-card-three {
		.home-card-item {
			height: 400px;
			width: 100%;
			overflow: hidden;
			.home-monitor {
				height: 100%;
				.flex-warp-item {
					width: 25%;
					height: 111px;
					display: flex;
					.flex-warp-item-box {
						margin: auto;
						text-align: center;
						color: var(--el-text-color-primary);
						display: flex;
						border-radius: 5px;
						background: var(--next-bg-color);
						cursor: pointer;
						transition: all 0.3s ease;
						&:hover {
							background: var(--el-color-primary-light-9);
							transition: all 0.3s ease;
						}
					}
					@for $i from 0 through $homeNavLengh {
						.home-animation#{$i} {
							opacity: 0;
							animation-name: error-num;
							animation-duration: 0.5s;
							animation-fill-mode: forwards;
							animation-delay: calc($i/10) + s;
						}
					}
				}
			}
		}
	}
}
</style>
