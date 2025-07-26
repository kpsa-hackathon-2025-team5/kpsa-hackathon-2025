import type { Member } from "~/types/member";
import { useApi } from "~/composable/useApi";

export const useAuthStore = defineStore("auth", () => {
  const { apiCall } = useApi();

  const member = ref<Member | null>(null);
  const isAuthenticated = computed(() => !!member.value);
  const memberId = computed<number | null>(() =>
    member.value ? member.value.id : null
  );

  const login = async (username: string, password: string) => {
    console.log("login");
    const response: any = await apiCall("/api/v1/auth/login", {
      method: "POST",
      body: JSON.stringify({ username, password }),
    });
    if (response) {
      member.value = response;
    } else {
      throw new Error("로그인 실패: 유효하지 않은 응답");
    }
  };

  const logout = () => {
    member.value = null;
  };

  return { member, isAuthenticated, login, logout, memberId };
});
