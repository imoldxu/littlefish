import APIFunction from "../../services";

const {getGoodsDetail} = APIFunction

export default {
  namespace: 'goodsDetail',
  state: {
  },
  subscriptions: {
    // fix: subscriptions 始终传入dispatch, app._history, taro 的 app._history 没有定义
  },
  reducers: {
    save(state, { payload }) {
      return { ...state, ...payload };
    },
    saveMore(state, { payload }) {
      const { list, pagination } = payload
      return { ...state, list: [...state.list, ...list], pagination };
    },
  },
  effects: {
    *get ({payload}, {call, put}){
      const {success, data} = yield call(getGoodsDetail, payload)
      if(success){
        yield put({type:'save', payload: data})
      }
    }
  },
}