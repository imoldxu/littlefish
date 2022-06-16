import BraftEditor from "braft-editor"

//若后端想存带有美化样式的html则使用该方式，否则直接存content内容，暂时不用
export const editorState2Html = (editorState)=>{
    return `
        <!Doctype html>
        <html>
        <head>
            <style>
            html,body{
                height: 100%;
                margin: 0;
                padding: 0;
                overflow: auto;
                background-color: #f1f2f3;
            }
            .x-html-container{
                box-sizing: border-box;
                width: 100%;
                max-width: 100%;
                min-height: 100%;
                margin: 0 auto;
                padding: 10px 10px;
                overflow: hidden;
                background-color: #fff;
                border-right: solid 1px #eee;
                border-left: solid 1px #eee;
            }
            .x-html-container img,
            .x-html-container audio,
            .x-html-container video{
                max-width: 100%;
                height: auto;
            }
            .x-html-container p{
                white-space: pre-wrap;
                margin-bottom: 0px;
            }
            .x-html-container pre{
                padding: 15px;
                background-color: #f1f1f1;
                border-radius: 5px;
            }
            .x-html-container blockquote{
                margin: 0;
                padding: 15px;
                background-color: #f1f1f1;
                border-left: 3px solid #d1d1d1;
            }
            .x-html-container li:before{
                content: "";
                width: 6px;
                height: 6px;
                display: inline-block;
                border-radius: 50%;
                background: black;
                vertical-align: middle;
                margin-right: 14px;
            }
            </style>
        </head>
        <body>
            <div className="x-html-container">${editorState.toHTML()}</div>
        </body>
        </html>
    `
}

export const html2EditorState = (html)=>{

    let divElement = document.createElement("div")
    divElement.innerHTML = html
    const containerElement = divElement.lastElementChild
    const content = containerElement.outerHTML
    const editor = BraftEditor.createEditorState(content);
    console.log(editor.toHTML())
    return BraftEditor.createEditorState(content)
}