import { get, post, put, delWithParams } from '@/utils/request'

const prefix = "/api/mobile/"

export function getUserImageRedirectPageList(data) {
  return get(prefix + 'userImageRedirect/getUserImageRedirectPageList', data)
}