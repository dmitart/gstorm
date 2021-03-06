package gstorm

class ClassMetaData {
    final def modelClass, tableName
    final List<FieldMetaData> fields
    private Map _fieldsMap

    ClassMetaData(Class modelClass) {
        this.modelClass = modelClass
        this.tableName = extractTableName(modelClass)
        this.fields = modelClass.declaredFields.findAll { !it.synthetic }.collect { field ->  new FieldMetaData(field) }
        this._fieldsMap = this.fields.collectEntries {fieldMetaData -> [fieldMetaData.name, fieldMetaData]}
    }

    FieldMetaData getAt(String fieldName) {
        this._fieldsMap[fieldName]
    }

    private String extractTableName(Class modelClass) {
        modelClass.getAnnotation(Table)?.value()?.trim() ?: modelClass.simpleName
    }

}
