import { useState } from "react";

export default function useEroll() {
  const [state, setState] = useState({})
  //   save(state, { payload }) {
  //     return { ...state, ...payload };
  //   },
  //   saveMore(state, { payload }) {
  //     const { list, pagination } = payload
  //     return { ...state, list: [...state.list, ...list], pagination };
  //   },
  // },
  // effects: {
  // },
  return {state}
}