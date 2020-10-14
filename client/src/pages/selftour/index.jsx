import React , {Component} from 'react'
import Taro from '@tarojs/taro'
import VirtualList from '@tarojs/components/virtual-list'
import { connect } from 'react-redux'
import {getWindowHeight} from '../../utils/uicalc'
import GoodsItemRow from '../../components/goodsItemRow'


@connect(({ selfTour }) => ({ selfTour }))
class GroupTour extends Component {

    componentWillMount() {
        const {dispatch} = this.props
        const vHeight = getWindowHeight(true)
        dispatch({type:'groupTour/save', payload:{vHeight: vHeight}})
    }

    loading = false

    render(){

        const { selfTour } = this.props;
        const { vHeight, data=[]} = selfTour

        const dataLen = data.length
        
        this.loading = false;
        const itemSize = 100;

        return (
            <VirtualList
                height={vHeight} /* 列表的高度 */
                width='100%' /* 列表的宽度 */
                itemData={data} /* 渲染列表的数据 */
                itemCount={dataLen} /*  渲染列表的长度 */
                itemSize={100} /* 列表单项的高度  */
                onScroll={({ scrollDirection, scrollOffset }) => {
                    if (
                      // 避免重复加载数据
                      !this.loading &&
                      // 只有往前滚动我们才触发
                      scrollDirection === 'forward' &&
                      // 5 = (列表高度 / 单项列表高度)
                      // 100 = 滚动提前加载量，可根据样式情况调整
                      scrollOffset > ((dataLen - 5) * itemSize + 100)
                    ) {
                        this.loading = true
                      this.listReachBottom()
                    }
                  }}
            >
                {GoodsItemRow}
            </VirtualList>
        )
    }

}

export default GroupTour;