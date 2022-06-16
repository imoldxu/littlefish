/** 仅仅是为了撑起页面，因为stepForm外层使用了max-content，为了让tabs的页面不跳动 */
const EmptyLine = ()=>{
    return (<div style={{width: "100vw", height: "0px"}}></div>)
}

export default EmptyLine;