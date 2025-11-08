import { useMutation } from '@tanstack/react-query';
import { useAuthStore } from '@/store/authStore';

interface LoginPayload {
    email: string;
    password: string;
}

export const useLoginMutation = () => {
    const login = useAuthStore((s) => s.login);

    return useMutation({
        mutationFn: async ({ email, password }: LoginPayload) => {
            const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/auth/auth/login`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ email, password }),
            });

            if (!res.ok) {
                const error = await res.text();
                throw new Error(error || 'Invalid email or password');
            }

            const data = await res.json();
            login(data.token);
        },
    });
};
