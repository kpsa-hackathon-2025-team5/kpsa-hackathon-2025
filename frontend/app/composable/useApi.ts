import { useAuthStore } from "~/stores/useAuthStore";

export const useApi = () => {
  const config = useRuntimeConfig();
  const baseURL = config.public.apiBaseUrl;

  const { memberId } = storeToRefs(useAuthStore());

  const apiCall = async (endpoint: string, options: any = {}) => {
    try {
      return await $fetch(`${baseURL}${endpoint}`, {
        headers: {
          "Content-Type": "application/json",
          "ngrok-skip-browser-warning": "69420",
          ...options.headers,
        },
        ...options,
      });
    } catch (error) {
      console.error("API 호출 오류:", error);
      throw error;
    }
  };

  const getImageUrl = (imagePath: string | undefined) => {
    if (!imagePath) {
      return `${baseURL}/api/v1/storage/image/default`;
    }
    //imagePath에 pravatar 가 포함되면 그대로 반환
    if (imagePath && imagePath.includes("pravatar")) {
      return imagePath;
    }

    return `${baseURL}/api/v1/storage/image/${imagePath}`
  }

  const authenticatedCall = async (endpoint: string, options: any = {}) => {
    if (!memberId) {
      throw new Error("사용자가 인증되지 않았습니다.");
    }
    return apiCall(endpoint, {
      ...options,
      headers: {
        Authorization: `Bearer ${memberId.value}`,
        "ngrok-skip-browser-warning": "69420",
        ...options.headers,
      },
    });
  };

  return {
    apiCall,
    authenticatedCall,
    getImageUrl
  };
};
