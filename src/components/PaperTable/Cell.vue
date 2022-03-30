<template>
    <td ref="cell" :class="[type, type === 'string' && val.elipsis ? 'elipsis' : null]">
        <span v-if="type === 'string'">{{ val.text }}</span>
        <span v-else-if="type === 'button'">
            <i
                :title="value.title || null"
                :class="value.icon || 'bi-balloon-fill'"
                @click.stop="value.click.call(null, row, $event)"
            ></i>
        </span>
        <span v-else-if="type === 'icon'">
            <i :class="value.icon" :title="value.title || null"></i>
        </span>
        <span v-else-if="type === 'image'">
            <img :src="value.src" alt="加载失败" />
        </span>
        <span v-else-if="type === 'checkbox'">
            <input type="checkbox" class="form-check-input" />
        </span>
    </td>
</template>

<script>
/**
 * Use inside a tr element only!
 * Props:
 *  row: the json data of the row where the cell is
 *  value: the cell actual value should be rendered, below will show possible use cases of how to pass in value
 * 1. display cell as string
 * :value = 'val'
 * :value = 520
 * :value = { type: 'string/str/text', text: 'whatever even a object', elipsis: true } // if set elipsis to true, text with truncated text and add '...' after it when text length exceeds column's width
 * 2. display cell as an icon button
 * :value = {
 *      type: 'btn/button',
 *      icon: 'bi-balloon-fill',
 *      color: '#000',
 *      hoverColor: '#e00',
 *      title: 'hint of the button',
 *      click: function(row, event) {
 *          // pass in a function, it exposes two arguments,
 *          //  first one for json data of the current row passed in, another for event object
 *      }
 * }
 * 3. display cell as image
 * :value = {
 *      type: 'img/image',
 *      src: '[image url link]',
 *      size: '3em',
 *      borderRadius: '.5em'
 * }
 * 4. display cell as icon
 * :value = {
 *      type: 'i/icon',
 *      icon: 'bi-heart',
 *      color: '#e00',
 *      title: 'hint of the icon'
 * }
 */
import formatDate from "./TimeUtil.js";
export default {
    data() {
        return {
            type: null,
            val: null
        }
    },
    props: {
        value: {
            type: null,
            require: true
        },
        header: null,
        row: Object
    },
    mounted() {
        if (['string', 'number'].includes(typeof this.value) || this.value == null) {
            this.type = 'string';
            this.val = { text: this.value || "" };
            if (this.header.time != null && this.value != null)
                this.val.text = formatDate(this.val.text, this.header.time);
            return;
        }
        switch (this.value.type) {
            case 'string':
            case 'str':
            case 'text':
                this.type = 'string';
                this.val = { text: this.value.text, elipsis: this.value.elipsis || false };
                if (this.header.time != null)
                    this.val.text = formatDate(this.value, this.header.time);
                break;
            case 'img':
            case 'image':
                this.type = 'image';
                this.$refs.cell.style.setProperty('--pt-img-size', this.value.size || null);
                this.$refs.cell.style.setProperty('--pt-img-border-radius', this.value.borderRadius || null);
                break;
            case 'i':
            case 'icon':
                this.type = 'icon';
                this.$refs.cell.style.setProperty('--pt-icon-color', this.value.color || null);
                break;
            case 'cb':
            case 'check':
            case 'checkbox':
                this.type = 'checkbox';
                break;
            case 'btn':
            case 'button':
                this.type = 'button';
                this.$refs.cell.style.setProperty('--pt-icon-color', this.value.color || null);
                this.$refs.cell.style.setProperty('--pt-icon-color-hover', this.value.hoverColor || this.value.color || null);
                break;
        }
    }
}
</script>

<style lang="scss" scoped>
td {
    &.string {
        span {
            letter-spacing: 0.03em;
        }

        &.elipsis {
            max-width: 0;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
        }
    }

    &.button i {
        @media (min-width: 768px) {
            position: absolute;
            transform: translate(-50%, -50%);
            transform-origin: center;
        }
        transition: transform 0.25s;
        font-size: 1.25em;
        color: var(--pt-icon-color, #000);

        &:hover {
            transform: translate(-50%, -50%) scale(2.5);
            color: var(--pt-icon-color-hover, var(--bs-danger));
        }
    }

    &.image img {
        width: var(--pt-img-size, 3em);
        border-radius: var(--pt-img-border-radius, 0.5em);
    }

    &.icon i {
        color: var(--pt-icon-color, initial);
    }
}
</style>