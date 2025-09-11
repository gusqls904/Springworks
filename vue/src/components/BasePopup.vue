<template>
  <div v-if="isVisible" class="base-popup-overlay" @click="handleOverlayClick">
    <div class="base-popup-container" :class="popupClass" @click.stop>
      <div class="base-popup-header" v-if="showHeader">
        <h3 class="base-popup-title">{{ title }}</h3>
        <button 
          v-if="showCloseButton" 
          class="base-popup-close" 
          @click="closePopup"
          aria-label="닫기"
        >
          <i class="fas fa-times"></i>
        </button>
      </div>
      
      <div class="base-popup-content">
        <slot></slot>
      </div>
      
      <div class="base-popup-footer" v-if="$slots.footer">
        <slot name="footer"></slot>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, watch } from 'vue'

export default {
  name: 'BasePopup',
  props: {
    visible: {
      type: Boolean,
      default: false
    },
    title: {
      type: String,
      default: ''
    },
    showHeader: {
      type: Boolean,
      default: true
    },
    showCloseButton: {
      type: Boolean,
      default: true
    },
    closeOnOverlay: {
      type: Boolean,
      default: false  // 기본값을 false로 변경
    },
    size: {
      type: String,
      default: 'medium', // small, medium, large, xlarge
      validator: (value) => ['small', 'medium', 'large', 'xlarge'].includes(value)
    },
    customClass: {
      type: String,
      default: ''
    }
  },
  emits: ['update:visible', 'close'],
  setup(props, { emit }) {
    const isVisible = ref(props.visible)
    
    const popupClass = ref([
      `base-popup-${props.size}`,
      props.customClass
    ])
    
    watch(() => props.visible, (newVal) => {
      isVisible.value = newVal
      if (newVal) {
        document.body.style.overflow = 'hidden'
      } else {
        document.body.style.overflow = ''
      }
    })
    
    const closePopup = () => {
      isVisible.value = false
      emit('update:visible', false)
      emit('close')
      document.body.style.overflow = ''
    }
    
    const handleOverlayClick = () => {
      if (props.closeOnOverlay) {
        closePopup()
      }
    }
    
    return {
      isVisible,
      popupClass,
      closePopup,
      handleOverlayClick
    }
  }
}
</script>

<style scoped>
.base-popup-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
  animation: fadeIn 0.3s ease;
}

.base-popup-container {
  background: white;
  border-radius: 12px;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
  max-height: 90vh;
  overflow: hidden;
  animation: slideIn 0.3s ease;
  position: relative;
}

.base-popup-small {
  width: 90%;
  max-width: 400px;
}

.base-popup-medium {
  width: 90%;
  max-width: 600px;
}

.base-popup-large {
  width: 90%;
  max-width: 800px;
}

.base-popup-xlarge {
  width: 90%;
  max-width: 1000px;
}

.base-popup-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px 24px;
  border-bottom: 1px solid #e5e7eb;
  background: #f9fafb;
}

.base-popup-title {
  margin: 0;
  font-size: 18px;
  font-weight: 600;
  color: #111827;
}

.base-popup-close {
  background: none;
  border: none;
  font-size: 20px;
  color: #6b7280;
  cursor: pointer;
  padding: 4px;
  border-radius: 4px;
  transition: all 0.2s ease;
}

.base-popup-close:hover {
  background: #f3f4f6;
  color: #374151;
}

.base-popup-content {
  padding: 24px;
  max-height: 60vh;
  overflow-y: auto;
}

.base-popup-footer {
  padding: 16px 24px;
  border-top: 1px solid #e5e7eb;
  background: #f9fafb;
  display: flex;
  justify-content: flex-end;
  gap: 12px;
}

@keyframes fadeIn {
  from { opacity: 0; }
  to { opacity: 1; }
}

@keyframes slideIn {
  from { 
    opacity: 0;
    transform: translateY(-20px) scale(0.95);
  }
  to { 
    opacity: 1;
    transform: translateY(0) scale(1);
  }
}

/* 반응형 */
@media (max-width: 768px) {
  .base-popup-container {
    margin: 20px;
    max-height: calc(100vh - 40px);
  }
  
  .base-popup-content {
    padding: 20px;
    max-height: calc(100vh - 120px);
  }
  
  .base-popup-header {
    padding: 16px 20px;
  }
  
  .base-popup-footer {
    padding: 12px 20px;
  }
}
</style>
