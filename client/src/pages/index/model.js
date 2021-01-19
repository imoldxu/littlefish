import APIFunction from '../../services'

const { queryBanner } = APIFunction

export default {
  namespace: 'index',
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
    *queryBanner({payload},{call, put}){
      const {success, data} = yield call(queryBanner, payload)
      if(success){
        yield put({type:'save', payload: {bannerlist: data}})
      }
    }
  },
}