<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <style>
        html,body {
            margin: 0;
            height:100%;
        }
    </style>
</head>
<body>
<table id="Student_Table"></table>
</body>
<script type="text/javascript">
    var Address = [{ "value": "1", "text": "CHINA" }, { "value": "2", "text": "USA" }, { "value": "3", "text": "Koren" }];


    function unitformatter(value, rowData, rowIndex) {
        if (value == 0) {
            return;
        }

        for (var i = 0; i < Address.length; i++) {
            if (Address[i].value == value) {
                return Address[i].text;
            }
        }
    }
    function GetTable() {
        var editRow = undefined;

        $("#Student_Table").datagrid({
            height: 300,
            width: 450,
            title: '学生表',
            collapsible: true,
            singleSelect: true,
            url: 'student.json',
            idField: 'ID',
            columns: [[
                { field: 'ID', title: 'ID', width: 100 },
                { field: 'Name', title: '姓名', width: 100, editor: { type: 'text', options: { required: true } } },
                { field: 'Age', title: '年龄', width: 100, align: 'center', editor: { type: 'text', options: { required: true } } },
                { field: 'Address', title: '地址', width: 100, formatter: unitformatter, align: 'center', editor:
                { type: 'combobox', options: { data: Address, valueField: "value", textField: "text" } } }
            ]],
            toolbar: [{
                text: '添加', iconCls: 'icon-add', handler: function () {
                    if (editRow != undefined) {
                        $("#Student_Table").datagrid('endEdit', editRow);
                    }
                    if (editRow == undefined) {
                        $("#Student_Table").datagrid('insertRow', {
                            index: 0,
                            row: {}
                        });

                        $("#Student_Table").datagrid('beginEdit', 0);
                        editRow = 0;
                    }
                }
            }, '-', {
                text: '保存', iconCls: 'icon-save', handler: function () {
                    $("#Student_Table").datagrid('endEdit', editRow);

                    //如果调用acceptChanges(),使用getChanges()则获取不到编辑和新增的数据。

                    //使用JSON序列化datarow对象，发送到后台。
                    var rows = $("#Student_Table").datagrid('getChanges');

                    var rowstr = JSON.stringify(rows);
                    $.post('/Home/Create', rowstr, function (data) {

                    });
                }
            }, '-', {
                text: '撤销', iconCls: 'icon-redo', handler: function () {
                    editRow = undefined;
                    $("#Student_Table").datagrid('rejectChanges');
                    $("#Student_Table").datagrid('unselectAll');
                }
            }, '-', {
                text: '删除', iconCls: 'icon-remove', handler: function () {
                    var row = $("#Student_Table").datagrid('getSelections');

                }
            }, '-', {
                text: '修改', iconCls: 'icon-edit', handler: function () {
                    var row = $("#Student_Table").datagrid('getSelected');
                    if (row != null) {
                        if (editRow != undefined) {
                            $("#Student_Table").datagrid('endEdit', editRow);
                        }

                        if (editRow == undefined) {
                            var index = $("#Student_Table").datagrid('getRowIndex', row);
                            $("#Student_Table").datagrid('beginEdit', index);
                            editRow = index;
                            $("#Student_Table").datagrid('unselectAll');
                        }
                    } else {

                    }
                }
            }, '-', {
                text: '上移', iconCls: 'icon-up', handler: function () {
                    MoveUp();
                }
            }, '-', {
                text: '下移', iconCls: 'icon-down', handler: function () {
                    MoveDown();
                }
            }],
            onAfterEdit: function (rowIndex, rowData, changes) {
                editRow = undefined;
            },
            onDblClickRow: function (rowIndex, rowData) {
                if (editRow != undefined) {
                    $("#Student_Table").datagrid('endEdit', editRow);
                }

                if (editRow == undefined) {
                    $("#Student_Table").datagrid('beginEdit', rowIndex);
                    editRow = rowIndex;
                }
            },
            onClickRow: function (rowIndex, rowData) {
                if (editRow != undefined) {
                    $("#Student_Table").datagrid('endEdit', editRow);

                }

            }

        });
    }
</script>
</html>