const Tool = {
    // check if an item has children
    hasChildren(item) {
        return item.children && item.children.length > 0;
    },
    // link unique id for each item
    tagUniqueId: (() => {
        let id = 0;
        return (items) => {
            if ('id' in items[0]) {
                id = 0;
                return;
            }
            for (let item of items) {
                if (Tool.hasChildren(item)) {
                    item.id = id++;
                    Tool.tagUniqueId(item.children);
                }
                item.id = id++;
            }
        }
    })(),
    initializeCheckStatus(items) {
        for (let item of items) {
            if (this.hasChildren(item)) {
                this.initializeCheckStatus(item.children);
                let uncheckedChildrenCount = item.children ? item.children.filter(ele => ele.checked === 0).length : 0;
                let indeterminateChildrenCount = item.children ? item.children.filter(ele => ele.checked === 1).length : 0;
                if (uncheckedChildrenCount === 0) {
                    if (indeterminateChildrenCount > 0) // no child is unchecked, but has children that are indeterminate, indeterminate parent
                        item.checked = 1;
                    else // no children is unchecked, check parent
                        item.checked = 2;

                }
                else if (uncheckedChildrenCount < item.children.length)
                    item.checked = 1; // children partial checked, indeterminate parent
                else
                    item.checked = 0; // children all unchecked, uncheck parent
            } else
                if (!('checked' in item))
                    item.checked = 0; // default is not checked
        }
    },
    /**
     * set self and its descendants to checked or unchecked
     * @param item target
     * @param checked true for check while false uncheck
     */
    setSelfAndAllDecendantsTo(item, checked) {
        const setTo = checked ? 2 : 0;
        if (this.hasChildren(item)) {
            for (let child of item.children)
                this.setSelfAndAllDecendantsTo(child, checked);
        }
        item.checked = setTo;
    }
}

export default Tool;