import { Grid } from 'annar';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React from 'react';
import { Text, View, Image } from 'remax/wechat';
import styles from './timeline.less'

export default class Timeline extends React.PureComponent {

    renderGridItem = (image, index)=>{
        return (
            <Image src={image} mode="widthFix" style={{width:"100%"}}></Image>
        )
    }

    render() {
        const { lastday, places, items, customStyle } = this.props;
        const rootClassName = [styles['at-timeline']];
        if (lastday)
            rootClassName.push(styles['at-timeline-lastday'])
        // const rootClassObject = {
        //     'at-timeline--pending': pending
        // };
        // const itemElems = items.map((item, index) => {
        //     const { title = '', info, icon='time', imageUrlList = [] } = item;
        //     const iconClass = classNames({
        //         'at-icon': true,
        //         [`at-icon-${icon}`]: icon
        //     });
        //     const itemRootClassName = ['at-timeline-item'];
        //     // if (color)
        //     //     itemRootClassName.push(`at-timeline-item--${color}`);
        //     const dotClass = [];
        //     if (icon) {
        //         dotClass.push('at-timeline-item__icon');
        //     }
        //     else {
        //         dotClass.push('at-timeline-item__dot');
        //     }
        //     return (
        //         React.createElement(View, { className: classNames(itemRootClassName), key: `at-timeline-item-${index}` },
        //             React.createElement(View, { className: 'at-timeline-item__tail' }),
        //             React.createElement(View, { className: classNames(dotClass) }, icon && React.createElement(Icon, { type: icon })),//React.createElement(Text, { className: iconClass })),
        //             React.createElement(View, { className: 'at-timeline-item__content' },
        //                 React.createElement(View, { className: 'at-timeline-item__content-item' }, title),
        //                 React.createElement(View,{ className: 'at-timeline-item__content-item at-timeline-item__content--sub' }, info))))
        //                 //imageUrlList.length>0? React.createElement(Grid, { className: 'at-timeline-item__content-item', data: imageUrlList, columns: imageUrlList.length>3 ? 3:item.imageUrlList.length }, renderGridItem))));
        // });
        // return (React.createElement(View, { className: classNames(rootClassName, rootClassObject, this.props.className), style: customStyle }, itemElems));
        
        

        return (<View className={classNames(rootClassName, this.props.className)} style={customStyle}>
                <View className={styles['at-timeline-item']}>
                    <View className={styles['at-timeline-item__tail']}></View>
                    <Image className={styles['at-timeline-item__icon']} src='/images/start.png' mode="scaleToFill"></Image>
                    <View className={styles['at-timeline-item__content']}>
                        <View className={styles['at-timeline-item__content--title']}>{places}</View>
                    </View>
                </View>
            {items.map(( item, index)=>{
                const { title, info, type, imageUrlList = [] } = item;
                // const iconClass = classNames({
                //     'at-icon': true,
                //     [`at-icon-${icon}`]: icon
                // });
                let icon = undefined
                switch(type){
                    case 1:
                        icon = '/images/free.png'
                        break;
                    case 2:
                        icon = '/images/meal.png'
                        break;
                    case 3:
                        icon = '/images/hotel.png'
                        break;
                    case 4:
                        icon = '/images/traffic.png'
                        break;
                    case 5:
                        icon = '/images/spot.png'
                        break;
                    default:
                        icon = '/images/start.png'
                }
                const itemRootClassName = [styles['at-timeline-item']];
                // if (color)
                //     itemRootClassName.push(`at-timeline-item--${color}`);
                const dotClass = [];
                if (icon) {
                    dotClass.push(styles['at-timeline-item__icon']);
                }
                else {
                    dotClass.push(styles['at-timeline-item__dot']);
                }
                return(
                <View className={classNames(itemRootClassName)} key={"time-line-item-"+index}>
                    <View className={styles['at-timeline-item__tail']}></View>
                    {/* <View className={classNames(dotClass)}>
                         <Icon type={icon} className={styles.timelineItem}></Icon>
                    </View> */}
                    <Image className={classNames(dotClass)} src={icon} mode="scaleToFill"></Image>
                    <View className={styles['at-timeline-item__content']}>
                        <View className={styles['at-timeline-item__content--title']}>{title}</View>
                        <View className={styles['at-timeline-item__content-item--sub']}>{info}</View>
                        {
                            imageUrlList && imageUrlList.length>0 ? (
                            <Grid data={imageUrlList} columns={             //className={styles['at-timeline-item__content-item']}
                                imageUrlList.length>3 ? 3:imageUrlList.length }>
                                {this.renderGridItem}
                            </Grid>):(<></>)
                        }
                    </View>
                </View>)
            })}
        </View>)
    }
}
Timeline.defaultProps = {
    pending: false,
    lastday: true,
    place: "",
    items: [],
    customStyle: {}
};
Timeline.propTypes = {
    pending: PropTypes.bool,
    lastday: PropTypes.bool,
    place: PropTypes.string,
    items: PropTypes.arrayOf(PropTypes.object),
    customStyle: PropTypes.oneOfType([PropTypes.object, PropTypes.string])
};