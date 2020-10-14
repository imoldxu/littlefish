
export default {
  namespace: 'enroll',
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
  },
}