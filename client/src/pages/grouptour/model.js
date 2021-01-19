import APIFunction from '../../services/api'

const {queryGroupTour} = APIFunction

export default {
  namespace: 'groupTour',
  state: {
    vHeight: 800,
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
    *query({payload},{call, put}){
       const {success, data} = yield call(queryGroupTour, payload)
       if(success){
         yield put({type: 'saveMore', payload: data})
       } 
    }
  },
}