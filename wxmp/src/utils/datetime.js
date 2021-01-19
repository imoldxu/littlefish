import moment from 'moment';

/*当前时间月的开始时间
*/
export function getMonthFirstDay(date){
    //var now = new Date(); //当前日期 
    // const nowMonth = now.getMonth(); //当前月 
    // const nowYear = now.getFullYear(); //当前年 
    // const monthStartDate = new Date(nowYear, nowMonth, 1);
    //return moment(monthStartDate).format('YYYY-MM-DD')
    return moment(date).startOf('month').format('YYYY-MM-DD')
}

/*当前时间月的结束时间
*/
export function getMonthLastDay (date) {
    // const nowMonth = now.getMonth(); //当前月 
    // const nowYear = now.getFullYear(); //当前年 
    // const monthEndDate = new Date(nowYear, nowMonth+1, 0);
    // return moment(monthEndDate).format('YYYY-MM-DD')
    return moment(date).endOf('month').format('YYYY-MM-DD')
}